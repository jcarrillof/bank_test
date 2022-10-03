package com.example.bank_test.service;

import com.example.bank_test.model.dto.MovimientoRequestDTO;
import com.example.bank_test.model.dto.MovimientoResponseDTO;
import com.example.bank_test.model.entity.Cliente;
import com.example.bank_test.model.entity.Cuenta;
import com.example.bank_test.model.entity.Movimiento;
import com.example.bank_test.model.entity.Persona;
import com.example.bank_test.model.enums.TipoMovimiento;
import com.example.bank_test.repository.ClienteRepository;
import com.example.bank_test.repository.CuentaRepository;
import com.example.bank_test.repository.MovimientoRepository;
import com.example.bank_test.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

@Service
public class MovimientoService {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;
    public void saveMovimiento(MovimientoRequestDTO movimientoRequestDTO) {
        try {
            Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimientoRequestDTO.getNumeroCuenta());
            List<Movimiento> movimientos = movimientoRepository.findByCuentaId(cuenta.getId());
            double previousSaldo = cuenta.getSaldoInicial();
            if (!movimientos.isEmpty()) {
                previousSaldo = movimientos.get(movimientos.size()-1).getSaldo();
                cuenta.setSaldoInicial(previousSaldo);
                cuentaRepository.save(cuenta);
            }

            if (movimientoRequestDTO.getTipoMovimiento().equals(TipoMovimiento.DEBITO) && previousSaldo == 0) {
                throw new IllegalArgumentException("Saldo no disponible");
            }

            double valor = setValorSign(movimientoRequestDTO.getValor(), movimientoRequestDTO.getTipoMovimiento());
            double newSaldo = calculateSaldo(previousSaldo, valor);
            Movimiento movimiento = new Movimiento(movimientoRequestDTO.getTipoMovimiento(), valor, newSaldo, cuenta);
            movimientoRepository.save(movimiento);
            cuenta.setSaldoInicial(newSaldo);

        } catch (IllegalArgumentException exception) {
            throw exception;
        } catch (Exception exception) {
            throw new IllegalArgumentException("Problemas al hacer la transaccion");
        }
    }

    public List<MovimientoResponseDTO> getMovimientos() {
        Stream<Movimiento> movimientos = movimientoRepository.findAll().stream();
        return movimientos.map(Movimiento::toDto).toList();
    }

    public List<MovimientoResponseDTO> getMovimientosFechaUsuario(String startDate, String endDate, String identificacion) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Persona persona = personaRepository.findByIdentificacion(identificacion);
        Cliente cliente = clienteRepository.findByPersonaId(persona.getId());
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(cliente.getId());
        Stream<Movimiento> movimientosCuentas = movimientoRepository
                .findAllByCuentaIdInAndFechaBetween(cuentas.stream().map(Cuenta::getId).toList(),
                        LocalDate.parse(startDate, dateFormat).atStartOfDay(),
                        LocalDate.parse(endDate, dateFormat).atTime(LocalTime.MAX)).stream();

        return movimientosCuentas.map(Movimiento::toDtoWithFecha).toList();
    }

    private double setValorSign(double valor, TipoMovimiento movimiento) {
        if (movimiento.equals(TipoMovimiento.DEBITO)) {
            return -valor;
        }
        return valor;
    }

    private double calculateSaldo(double previousSaldo, double valor) {
        return previousSaldo + valor;
    }
}

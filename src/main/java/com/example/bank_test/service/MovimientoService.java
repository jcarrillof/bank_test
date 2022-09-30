package com.example.bank_test.service;

import com.example.bank_test.model.TipoMovimiento;
import com.example.bank_test.model.dto.MovimientoRequestDTO;
import com.example.bank_test.model.dto.MovimientoResponseDTO;
import com.example.bank_test.model.entity.Cliente;
import com.example.bank_test.model.entity.Cuenta;
import com.example.bank_test.model.entity.Movimiento;
import com.example.bank_test.model.entity.Persona;
import com.example.bank_test.repository.ClienteRepository;
import com.example.bank_test.repository.CuentaRepository;
import com.example.bank_test.repository.MovimientoRepository;
import com.example.bank_test.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Cuenta cuenta = cuentaRepository.findByNumeroCuenta(movimientoRequestDTO.getNumeroCuenta());
        List<Movimiento> movimientos = movimientoRepository.findByCuentaId(cuenta.getId());
        double previousSaldo = cuenta.getSaldoInicial();
        if (!movimientos.isEmpty()) {
            previousSaldo = movimientos.get(movimientos.size()-1).getSaldo();
            cuenta.setSaldoInicial(previousSaldo);
            cuentaRepository.save(cuenta);
        }

        if (movimientoRequestDTO.getTipoMovimiento().equals("debito") && previousSaldo == 0) {
            throw new IllegalArgumentException("Saldo no disponible");
        }

        double valor = setValorSign(movimientoRequestDTO.getValor(), movimientoRequestDTO.getTipoMovimiento());
        double newSaldo = calculateSaldo(previousSaldo, valor);
        Movimiento movimiento = new Movimiento(movimientoRequestDTO.getTipoMovimiento(), valor, newSaldo, cuenta);
        movimientoRepository.save(movimiento);
        cuenta.setSaldoInicial(newSaldo);
    }

    public List<MovimientoResponseDTO> getMovimientos() {
        Stream<Movimiento> movimientos = movimientoRepository.findAll().stream();
        return movimientos.map(Movimiento::toDto).toList();
    }

    public List<MovimientoResponseDTO> getMovimientosFechaUsuario(String fecha, String identificacion) {
        Persona persona = personaRepository.findByIdentificacion(identificacion);
        Cliente cliente = clienteRepository.findByPersonaId(persona.getId());
        List<Cuenta> cuentas = cuentaRepository.findByClienteId(cliente.getId());
        Stream<Movimiento> movimientosCuentas = movimientoRepository
                .findAllByCuentaIdIn(cuentas.stream().map(Cuenta::getId).toList()).stream();

        return movimientosCuentas.map(Movimiento::toDtoCuentasFecha).toList();
    }

    private double setValorSign(double valor, String movimiento) {
        if (movimiento.equals(TipoMovimiento.debito.toString())) {
            return -valor;
        }
        return valor;
    }

    private double calculateSaldo(double previousSaldo, double valor) {
        return previousSaldo + valor;
    }
}

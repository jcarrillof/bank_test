package com.example.bank_test.service;

import com.example.bank_test.model.dto.CuentaRequestDTO;
import com.example.bank_test.model.dto.CuentaResponseDTO;
import com.example.bank_test.model.entity.Cliente;
import com.example.bank_test.model.entity.Cuenta;
import com.example.bank_test.model.entity.Persona;
import com.example.bank_test.repository.ClienteRepository;
import com.example.bank_test.repository.CuentaRepository;
import com.example.bank_test.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CuentaService {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CuentaRepository cuentaRepository;

    public void saveCuenta(CuentaRequestDTO cuentaRequestDTO) {
        try {
            Persona persona = personaRepository.findByIdentificacion(cuentaRequestDTO.getIdentificacionCliente());
            Cliente cliente = clienteRepository.findByPersonaId(persona.getId());
            Cuenta cuenta = new Cuenta(cuentaRequestDTO.getNumeroCuenta(),
                    cuentaRequestDTO.getTipoCuenta(), cuentaRequestDTO.getSaldoInicial());
            cuenta.setCliente(cliente);
            cuentaRepository.save(cuenta);
        } catch (Exception exception) {
            throw new IllegalArgumentException("Problemas al crear la cuenta");
        }
    }

    public List<CuentaResponseDTO> getCuentas() {
        Stream<Cuenta> cuentas = cuentaRepository.findAll().stream();
        return cuentas.map(Cuenta::toDto).toList();
    }

    public CuentaResponseDTO getCuenta(Long id) {
        Cuenta cuenta = validateCuenta(id);
        return cuenta.toDto();
    }

    public void deleteCuenta(Long id) {
        Cuenta cuenta = validateCuenta(id);
        cuenta.setEstado("eliminado");
        cuentaRepository.save(cuenta);
    }

    private Cuenta validateCuenta(Long id) {
        Optional<Cuenta> cuentaOptional = cuentaRepository.findById(id);
        if (cuentaOptional.isEmpty()) {
            throw new IllegalArgumentException("Cuenta no existe");
        }
        return cuentaOptional.get();
    }
}

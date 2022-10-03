package com.example.bank_test.service;

import com.example.bank_test.model.dto.ClienteRequestDTO;
import com.example.bank_test.model.entity.Cliente;
import com.example.bank_test.model.entity.Persona;
import com.example.bank_test.repository.ClienteRepository;
import com.example.bank_test.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public void saveClienteInformation(ClienteRequestDTO clienteRequestDTO) {
        Persona persona = new Persona();
        persona.fromDto(clienteRequestDTO);
        Cliente cliente = new Cliente(clienteRequestDTO.getClienteId(), clienteRequestDTO.getContrasena());
        cliente.setPersona(persona);
        personaRepository.save(persona);
        clienteRepository.save(cliente);
    }

    public void updateCliente(Long id, ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = validateCliente(id);
        cliente.getPersona().fromDtoUpdate(clienteRequestDTO);
        cliente.setPersona(cliente.getPersona());
        clienteRepository.save(cliente);
    }

    public void deleteCliente(Long id) {
        Cliente cliente = validateCliente(id);
        cliente.setEstado("eliminado");
        clienteRepository.save(cliente);
    }

    private Cliente validateCliente(Long id) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new IllegalArgumentException("Cliente no existe en la base de datos");
        }
        return clienteOptional.get();
    }
}

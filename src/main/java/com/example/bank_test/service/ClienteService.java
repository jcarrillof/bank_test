package com.example.bank_test.service;

import com.example.bank_test.model.entity.Cliente;
import com.example.bank_test.model.dto.ClienteRequestDTO;
import com.example.bank_test.model.entity.Persona;
import com.example.bank_test.repository.ClienteRepository;
import com.example.bank_test.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {
    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public void saveClienteInformation(ClienteRequestDTO clienteRequestDTO) {
        Persona persona = new Persona();
        persona.setNombre(clienteRequestDTO.getNombre());
        persona.setGenero(clienteRequestDTO.getGenero());
        persona.setEdad(clienteRequestDTO.getEdad());
        persona.setIdentificacion(clienteRequestDTO.getIdentificacion());
        persona.setDireccion(clienteRequestDTO.getDireccion());
        persona.setTelefono(clienteRequestDTO.getTelefono());
        Cliente cliente = new Cliente(clienteRequestDTO.getClienteId(), clienteRequestDTO.getContrasena());
        cliente.setPersona(persona);
        personaRepository.save(persona);
        clienteRepository.save(cliente);
    }
}

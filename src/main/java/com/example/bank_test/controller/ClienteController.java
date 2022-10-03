package com.example.bank_test.controller;

import com.example.bank_test.model.dto.ClienteRequestDTO;
import com.example.bank_test.model.dto.ClienteResponseDTO;
import com.example.bank_test.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<String> createCliente(@RequestBody ClienteRequestDTO clienteRequestDTO)
    {
        clienteService.saveClienteInformation(clienteRequestDTO);
        return ResponseEntity.ok().body("Cliente creado exitosamente");
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<ClienteResponseDTO>> getAllClientes()
    {
        return ResponseEntity.ok(clienteService.getClientes());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteResponseDTO> getCliente(@PathVariable Long id)
    {
        return ResponseEntity.ok(clienteService.getCliente(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updateCliente(@PathVariable Long id,
                                                @RequestParam(required = false) String direccion,
                                                @RequestParam(required = false) String telefono)
    {
        ClienteRequestDTO clienteRequestDTO = new ClienteRequestDTO();
        clienteRequestDTO.setDireccion(direccion);
        clienteRequestDTO.setTelefono(telefono);
        clienteService.updateCliente(id, clienteRequestDTO);
        return ResponseEntity.ok().body("Información actualizada exitosamente");
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Long id)
    {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok().body("Cliente eliminado exitosamente");
    }
}

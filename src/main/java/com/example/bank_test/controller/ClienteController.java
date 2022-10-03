package com.example.bank_test.controller;

import com.example.bank_test.model.dto.ClienteRequestDTO;
import com.example.bank_test.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCliente(@RequestBody ClienteRequestDTO clienteRequestDTO)
    {
        clienteService.saveClienteInformation(clienteRequestDTO);
        return ResponseEntity.ok().body("Cliente creado exitosamente");
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
    @ResponseBody
    public ResponseEntity<String> deleteCliente(@PathVariable Long id)
    {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok().body("Cliente eliminado exitosamente");
    }
}

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

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteCliente(@PathVariable Long id)
    {
        clienteService.deleteCliente(id);
        return ResponseEntity.ok().body("Cliente eliminado exitosamente");
    }
}

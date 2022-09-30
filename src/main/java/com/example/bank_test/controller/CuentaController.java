package com.example.bank_test.controller;

import com.example.bank_test.model.dto.CuentaRequestDTO;
import com.example.bank_test.model.dto.CuentaResponseDTO;
import com.example.bank_test.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createCuenta(@RequestBody CuentaRequestDTO cuentaRequestDTO)
    {
        cuentaService.saveCuenta(cuentaRequestDTO);
        return ResponseEntity.ok().body("Cuenta creada exitosamente");
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CuentaResponseDTO>> getAllCuentas()
    {
        return ResponseEntity.ok(cuentaService.getCuentas());
    }
}

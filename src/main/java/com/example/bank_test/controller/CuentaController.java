package com.example.bank_test.controller;

import com.example.bank_test.model.dto.CuentaRequestDTO;
import com.example.bank_test.model.dto.CuentaResponseDTO;
import com.example.bank_test.service.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    @Autowired
    private CuentaService cuentaService;

    @PostMapping
    public ResponseEntity<String> createCuenta(@RequestBody CuentaRequestDTO cuentaRequestDTO)
    {
        cuentaService.saveCuenta(cuentaRequestDTO);
        return ResponseEntity.ok().body("Cuenta creada exitosamente");
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<CuentaResponseDTO>> getAllCuentas()
    {
        return ResponseEntity.ok(cuentaService.getCuentas());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CuentaResponseDTO> getCuenta(@PathVariable Long id)
    {
        return ResponseEntity.ok(cuentaService.getCuenta(id));
    }
}

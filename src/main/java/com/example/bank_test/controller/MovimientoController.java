package com.example.bank_test.controller;

import com.example.bank_test.model.dto.MovimientoRequestDTO;
import com.example.bank_test.model.dto.MovimientoResponseDTO;
import com.example.bank_test.service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createMovimiento(@RequestBody MovimientoRequestDTO movimientoRequestDTO)
    {
        movimientoService.saveMovimiento(movimientoRequestDTO);
        return ResponseEntity.ok().body("Transaccion realizada exitosamente");
    }

    @GetMapping(value = "/all",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MovimientoResponseDTO>> getAllMovimientos()
    {
        return ResponseEntity.ok(movimientoService.getMovimientos());
    }

    @GetMapping(value = "/fechaUsuario",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MovimientoResponseDTO>> getMovimientosFechaUsuario(@RequestParam String fechaInicio,
                                                                                  @RequestParam String fechaFinal,
                                                                                  @RequestParam String identificacion)
    {
        return ResponseEntity.ok(movimientoService.getMovimientosFechaUsuario(fechaInicio, fechaFinal,
                identificacion));
    }
}

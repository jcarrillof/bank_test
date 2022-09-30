package com.example.bank_test.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MovimientoRequestDTO {
    @Getter
    private String tipoMovimiento;

    @Getter
    private double valor;

    @Getter
    private String numeroCuenta;
}

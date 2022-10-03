package com.example.bank_test.model.dto;

import com.example.bank_test.model.enums.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MovimientoRequestDTO {
    @Getter
    private TipoMovimiento tipoMovimiento;

    @Getter
    private double valor;

    @Getter
    private String numeroCuenta;
}

package com.example.bank_test.model.dto;

import lombok.Getter;

public class CuentaRequestDTO {
    @Getter
    private String numeroCuenta;

    @Getter
    private String tipoCuenta;

    @Getter
    private double saldoInicial;

    @Getter
    private String identificacionCliente;
}

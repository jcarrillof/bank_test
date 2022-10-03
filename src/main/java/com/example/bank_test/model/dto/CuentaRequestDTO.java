package com.example.bank_test.model.dto;

import com.example.bank_test.model.enums.TipoCuenta;
import lombok.Getter;

public class CuentaRequestDTO {
    @Getter
    private String numeroCuenta;

    @Getter
    private TipoCuenta tipoCuenta;

    @Getter
    private double saldoInicial;

    @Getter
    private String identificacionCliente;
}

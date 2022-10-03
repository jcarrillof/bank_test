package com.example.bank_test.model.dto;

import com.example.bank_test.model.enums.TipoCuenta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CuentaResponseDTO implements Serializable{
    private String numeroCuenta;

    private TipoCuenta tipoCuenta;

    private double saldoInicial;

    private String estado;

    private String nombreCliente;
}

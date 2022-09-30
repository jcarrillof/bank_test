package com.example.bank_test.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CuentaResponseDTO implements Serializable{
    private String numeroCuenta;

    private String tipoCuenta;

    private double saldoInicial;

    private String estado;

    private String nombreCliente;
}

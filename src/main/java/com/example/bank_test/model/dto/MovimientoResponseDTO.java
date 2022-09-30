package com.example.bank_test.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovimientoResponseDTO implements Serializable{
    private LocalDateTime fecha;

    private String nombre;

    private String numeroCuenta;

    private String tipoCuenta;

    private double saldoInicial;

    private String estado;

    private String tipoMovimiento;

    private double valor;

    private double saldo;
}

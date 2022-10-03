package com.example.bank_test.model.dto;

import com.example.bank_test.model.enums.TipoCuenta;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovimientoResponseDTO implements Serializable{
    private LocalDate fecha;

    private String nombre;

    private String numeroCuenta;

    private TipoCuenta tipoCuenta;

    private double saldoInicial;

    private String estado;

    private String tipoMovimiento;

    private double valor;

    private double saldo;
}

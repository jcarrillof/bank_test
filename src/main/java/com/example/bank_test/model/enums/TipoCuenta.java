package com.example.bank_test.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoCuenta {
    @JsonProperty("ahorros")
    AHORROS,
    @JsonProperty("corriente")
    CORRIENTE
}

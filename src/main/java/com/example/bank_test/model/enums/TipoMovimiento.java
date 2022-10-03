package com.example.bank_test.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum TipoMovimiento {
    @JsonProperty("credito")
    CREDITO,
    @JsonProperty("debito")
    DEBITO
}

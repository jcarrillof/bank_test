package com.example.bank_test.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Genero {
    @JsonProperty("masculino")
    MASCULINO,
    @JsonProperty("femenino")
    FEMENINO
}

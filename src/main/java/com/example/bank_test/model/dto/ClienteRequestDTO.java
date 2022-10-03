package com.example.bank_test.model.dto;

import com.example.bank_test.model.enums.Genero;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequestDTO {
    @Getter
    private String nombre;

    @Getter
    private String identificacion;

    @Getter
    @Setter
    private String direccion;

    @Getter
    @Setter
    private String telefono;

    @Getter
    private Genero genero;

    @Getter
    private int edad;

    @Getter
    @JsonProperty("usuario")
    private String clienteId;

    @Getter
    private String contrasena;
}

package com.example.bank_test.model.dto;

import lombok.Data;

@Data
public class ClienteResponseDTO {
    private String nombre;

    private String direccion;

    private String telefono;

    private String contrasena;

    private String estado;
}

package com.example.bank_test.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Persona {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String genero;

    @Getter
    @Setter
    private int edad;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private String direccion;

    @Getter
    @Setter
    private String telefono;

    @OneToOne(mappedBy = "persona")
    private Cliente cliente;
}

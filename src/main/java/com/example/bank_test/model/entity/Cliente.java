package com.example.bank_test.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Cliente {

    public Cliente(String clienteId, String contrasena) {
        this.clienteId = clienteId;
        this.contrasena = contrasena;
        this.estado = "true";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Getter
    private String clienteId;

    @Getter
    private String contrasena;

    @Getter
    @Setter
    private String estado;

    @Setter
    @Getter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "persona_id")
    private Persona persona;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Cuenta> cuenta;
}

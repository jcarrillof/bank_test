package com.example.bank_test.model.entity;

import com.example.bank_test.model.dto.ClienteRequestDTO;
import com.example.bank_test.model.enums.Genero;
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
    @Enumerated(EnumType.STRING)
    private Genero genero;

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

    public Persona fromDto(ClienteRequestDTO clienteRequestDTO) {
        this.setNombre(clienteRequestDTO.getNombre());
        this.setGenero(clienteRequestDTO.getGenero());
        this.setEdad(clienteRequestDTO.getEdad());
        this.setIdentificacion(clienteRequestDTO.getIdentificacion());
        this.setDireccion(clienteRequestDTO.getDireccion());
        this.setTelefono(clienteRequestDTO.getTelefono());
        return this;
    }

    public Persona fromDtoUpdate(ClienteRequestDTO clienteRequestDTO) {
        if (clienteRequestDTO.getDireccion() != null) {
            this.setDireccion(clienteRequestDTO.getDireccion());
        }
        if (clienteRequestDTO.getTelefono() != null) {
            this.setTelefono(clienteRequestDTO.getTelefono());
        }
        return this;
    }
}

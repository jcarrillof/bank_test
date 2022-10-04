package com.example.bank_test.model.entity;

import com.example.bank_test.model.dto.CuentaResponseDTO;
import com.example.bank_test.model.enums.TipoCuenta;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Cuenta {

    public Cuenta(String numeroCuenta, TipoCuenta tipoCuenta, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoInicial = saldoInicial;
        this.estado = "true";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private long id;

    @Getter
    @Setter
    private String numeroCuenta;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoCuenta tipoCuenta;

    @Getter
    @Setter
    private double saldoInicial;

    @Getter
    @Setter
    private String estado;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Movimiento> movimiento;

    public CuentaResponseDTO toDto() {
        CuentaResponseDTO cuentaResponseDTO = new CuentaResponseDTO();

        cuentaResponseDTO.setNumeroCuenta(this.getNumeroCuenta());
        cuentaResponseDTO.setTipoCuenta(this.getTipoCuenta());
        cuentaResponseDTO.setSaldoInicial(this.getSaldoInicial());
        cuentaResponseDTO.setEstado(this.getEstado());
        cuentaResponseDTO.setNombreCliente(this.getCliente().getPersona().getNombre());

        return cuentaResponseDTO;
    }
}

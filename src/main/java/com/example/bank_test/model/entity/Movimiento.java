package com.example.bank_test.model.entity;

import com.example.bank_test.model.dto.MovimientoResponseDTO;
import com.example.bank_test.model.enums.TipoMovimiento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Movimiento {
    public Movimiento(TipoMovimiento tipoMovimiento, double valor, double saldo, Cuenta cuenta) {
        this.fecha = LocalDateTime.now();
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.saldo = saldo;
        this.cuenta = cuenta;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    private LocalDateTime fecha;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento;

    @Getter
    @Setter
    private double valor;

    @Getter
    @Setter
    private double saldo;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id")
    private Cuenta cuenta;

    public MovimientoResponseDTO toDto() {
        MovimientoResponseDTO movimientoResponseDTO = new MovimientoResponseDTO();

        movimientoResponseDTO.setNumeroCuenta(this.getCuenta().getNumeroCuenta());
        movimientoResponseDTO.setTipoCuenta(this.getCuenta().getTipoCuenta());
        movimientoResponseDTO.setSaldoInicial(this.getCuenta().getSaldoInicial());
        movimientoResponseDTO.setEstado(this.getCuenta().getEstado());
        movimientoResponseDTO.setTipoMovimiento(getMovimientoMessage());

        return movimientoResponseDTO;
    }

    public MovimientoResponseDTO toDtoWithFecha() {
        MovimientoResponseDTO movimientoResponseDTO = new MovimientoResponseDTO();

        movimientoResponseDTO.setFecha(this.getFecha().toLocalDate());
        movimientoResponseDTO.setNombre(this.getCuenta().getCliente().getPersona().getNombre());
        movimientoResponseDTO.setNumeroCuenta(this.getCuenta().getNumeroCuenta());
        movimientoResponseDTO.setTipoCuenta(this.getCuenta().getTipoCuenta());
        movimientoResponseDTO.setSaldoInicial(this.getCuenta().getSaldoInicial());
        movimientoResponseDTO.setEstado(this.getCuenta().getEstado());
        movimientoResponseDTO.setValor(this.getValor());
        movimientoResponseDTO.setSaldo(this.getSaldo());

        return movimientoResponseDTO;
    }

    private String getMovimientoMessage() {
        if (this.getTipoMovimiento().equals(TipoMovimiento.DEBITO)) {
            return "Retiro de " + Math.abs(this.getValor());
        }
        return "Deposito de " + this.getValor();
    }
}

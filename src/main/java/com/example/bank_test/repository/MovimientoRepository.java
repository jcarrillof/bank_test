package com.example.bank_test.repository;

import com.example.bank_test.model.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByCuentaId(long cuentaId);

    // List<Movimiento> findByFechaAndCuentaId(String fecha, Collection<Long> cuentaIds);
    List<Movimiento> findAllByCuentaIdIn(Collection<Long> cuentaIds);
}

package com.example.bank_test.repository;

import com.example.bank_test.model.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Persona findByIdentificacion(String identificacion);
}

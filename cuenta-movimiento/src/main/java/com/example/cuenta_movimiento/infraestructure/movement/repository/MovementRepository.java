package com.example.cuenta_movimiento.infraestructure.movement.repository;

import com.example.cuenta_movimiento.domain.movement.entity.Movement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movement, Long> {
    // Métodos adicionales si son necesarios
}

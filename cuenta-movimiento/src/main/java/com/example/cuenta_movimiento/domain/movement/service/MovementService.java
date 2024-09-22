package com.example.cuenta_movimiento.domain.movement.service;

import com.example.cuenta_movimiento.domain.movement.dto.MovementDTO;

import java.util.List;

public interface MovementService {
    MovementDTO createMovement(MovementDTO movementDTO);
    MovementDTO updateMovement(Long movementId, MovementDTO movementDTO);
    void deleteMovement(Long movementId);
    List<MovementDTO> getAllMovements();
    MovementDTO getMovementById(Long movementId);
}

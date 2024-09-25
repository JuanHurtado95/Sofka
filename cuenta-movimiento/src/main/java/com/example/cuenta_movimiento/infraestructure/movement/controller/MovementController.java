package com.example.cuenta_movimiento.infraestructure.movement.controller;

import com.example.cuenta_movimiento.domain.movement.dto.MovementDTO;
import com.example.cuenta_movimiento.domain.movement.service.MovementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/movimientos")
public class MovementController {

    @Autowired
    private MovementService movementService;

    @PostMapping
    public ResponseEntity<MovementDTO> createMovement(@Valid @RequestBody MovementDTO movementDTO) throws JsonProcessingException {
        MovementDTO createdMovement = movementService.createMovement(movementDTO);
        return new ResponseEntity<>(createdMovement, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MovementDTO>> getAllMovements() {
        List<MovementDTO> movements = movementService.getAllMovements();
        return new ResponseEntity<>(movements, HttpStatus.OK);
    }

    @GetMapping("/{movementId}")
    public ResponseEntity<MovementDTO> getMovementById(@PathVariable Long movementId) {
        MovementDTO movement = movementService.getMovementById(movementId);
        return new ResponseEntity<>(movement, HttpStatus.OK);
    }

    @PutMapping("/{movementId}")
    public ResponseEntity<MovementDTO> updateMovement(@PathVariable Long movementId, @Valid @RequestBody MovementDTO movementDTO) {
        MovementDTO updatedMovement = movementService.updateMovement(movementId, movementDTO);
        return new ResponseEntity<>(updatedMovement, HttpStatus.OK);
    }

    @DeleteMapping("/{movementId}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Long movementId) {
        movementService.deleteMovement(movementId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

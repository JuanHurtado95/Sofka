package com.example.cuenta_movimiento.domain.movement.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovementDTO {
    private LocalDate date;
    private String movementType;
    private Double value;
    private Long accountId; // ID de la cuenta asociada
}

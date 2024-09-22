package com.example.cuenta_movimiento.domain.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NotificationMovementDTO {
    private Long clientId;
    private LocalDate date;
    private String movementType;
    private Double value;
    private Long accountId;
}

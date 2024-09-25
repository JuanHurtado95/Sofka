package com.example.cuenta_movimiento.domain.movement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovementDTO {

    private Long movementId;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate date;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Pattern(regexp = "Deposito|Retiro", message = "El tipo de movimiento debe ser 'Deposito' o 'Retiro'")
    private String movementType;

    @NotNull(message = "El valor es obligatorio")
    @Positive(message = "El valor debe ser mayor que cero")
    private Double value;

    @NotNull(message = "El ID de la cuenta es obligatorio")
    private Long accountId;
}

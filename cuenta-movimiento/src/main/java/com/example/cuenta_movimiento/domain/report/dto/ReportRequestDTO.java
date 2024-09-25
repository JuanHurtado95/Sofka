package com.example.cuenta_movimiento.domain.report.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReportRequestDTO {

    @NotNull(message = "La fecha de inicio es obligatoria")
    @PastOrPresent(message = "La fecha de inicio debe ser hoy o una fecha pasada")
    private LocalDate startDate;

    @NotNull(message = "La fecha de fin es obligatoria")
    @FutureOrPresent(message = "La fecha de fin debe ser hoy o una fecha futura")
    private LocalDate endDate;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clientId;
}

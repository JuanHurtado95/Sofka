package com.example.cuenta_movimiento.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovementReportDTO {

    private LocalDate fecha;
    private String tipoMovimiento;
    private double valor;
}


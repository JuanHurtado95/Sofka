package com.example.cuenta_movimiento.domain.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReportRequestDTO {

    private LocalDate startDate;
    private LocalDate endDate;
    private Long clientId;
}

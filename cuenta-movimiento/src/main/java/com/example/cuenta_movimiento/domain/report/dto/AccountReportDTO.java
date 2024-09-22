package com.example.cuenta_movimiento.domain.report.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class AccountReportDTO {

    private String accountNumber;
    private Double availableBalance;
    private String accountType;
    private List<MovementReportDTO> movements;
}
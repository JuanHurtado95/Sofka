package com.example.cuenta_movimiento.domain.report.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ReportDTO {

    private String accountNumber;
    private Double availableBalance;
    private String accountType;
    private LocalDate date;
    private String movementType;
    private Double value;

    public ReportDTO(String accountNumber, Double availableBalance, String accountType, LocalDate date, String movementType, Double value) {
        this.accountNumber = accountNumber;
        this.availableBalance = availableBalance;
        this.accountType = accountType;
        this.date = date;
        this.movementType = movementType;
        this.value = value;
    }
}

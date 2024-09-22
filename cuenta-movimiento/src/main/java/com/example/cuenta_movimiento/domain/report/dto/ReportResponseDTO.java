package com.example.cuenta_movimiento.domain.report.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportResponseDTO {

    private String name;
    private List<AccountReportDTO> accounts;
}

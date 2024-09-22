package com.example.cuenta_movimiento.domain.report.service;

import com.example.cuenta_movimiento.domain.report.dto.ReportRequestDTO;
import com.example.cuenta_movimiento.domain.report.dto.ReportResponseDTO;

public interface IReportService {

    public ReportResponseDTO report(ReportRequestDTO request);
}

package com.example.cuenta_movimiento.infraestructure.report.controller;

import com.example.cuenta_movimiento.domain.report.dto.ReportRequestDTO;
import com.example.cuenta_movimiento.domain.report.dto.ReportResponseDTO;
import com.example.cuenta_movimiento.domain.report.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @PostMapping
    public ResponseEntity<ReportResponseDTO> generateReport(@RequestBody ReportRequestDTO request) {
        ReportResponseDTO report = reportService.report(request);
        return ResponseEntity.ok(report);
    }
}

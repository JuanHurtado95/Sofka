package com.example.cuenta_movimiento.domain.report.service;

import com.example.cuenta_movimiento.domain.report.dto.*;
import com.example.cuenta_movimiento.infraestructure.movement.repository.MovementRepository;
import com.example.cuenta_movimiento.infraestructure.report.repository.ReportRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements IReportService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private MovementRepository movementRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReportResponseDTO report(ReportRequestDTO request) {

        ReportResponseDTO result = new ReportResponseDTO();

        result.setName(clientConsult(request.getClientId()));

        List<ReportDTO> accountResults = reportRepository.getReport(request.getClientId(), request.getStartDate(), request.getEndDate());

        Map<String, List<ReportDTO>> groupedReportData = accountResults.stream()
                .collect(Collectors.groupingBy(ReportDTO::getAccountNumber));

        List<AccountReportDTO> accountReports = groupedReportData.entrySet().stream()
                .map(entry -> {
                    String numeroCuenta = entry.getKey();
                    List<ReportDTO> accountMovements = entry.getValue();

                    // Obtener saldo y tipo de cuenta (asumimos que son iguales para todos los movimientos de la cuenta)
                    Double saldo = accountMovements.get(0).getAvailableBalance();
                    String tipoCuenta = accountMovements.get(0).getAccountType();

                    // Crear lista de MovementReportDTO con manejo de valores nulos
                    List<MovementReportDTO> movimientos = accountMovements.stream()
                            .filter(movement -> movement.getDate() != null &&
                                    movement.getMovementType() != null &&
                                    movement.getValue() != null &&
                                    movement.getValue() != 0.0)
                            .map(movement -> new MovementReportDTO(
                                    movement.getDate(),
                                    movement.getMovementType(),
                                    movement.getValue()))
                            .collect(Collectors.toList());

                    // Crear AccountReportDTO
                    return new AccountReportDTO(numeroCuenta, saldo, tipoCuenta, movimientos);
                })
                .collect(Collectors.toList());

        result.setAccounts(accountReports);
        return result;
    }

    @Cacheable(value = "clients", key = "#clientId")
    @CircuitBreaker(name = "clientService", fallbackMethod = "clientFallback")
    private String clientConsult(Long clientId) {
        String url = "http://localhost:8080/api/v1/clientes/" + clientId;
        ClientReportDTO clientReportDTO = restTemplate.getForObject(url, ClientReportDTO.class);
        if (clientReportDTO != null) {
            return clientReportDTO.getName();
        } else {
            throw new RuntimeException("Client not found");
        }
    }

    private String clientFallback(Long clientId, Throwable throwable) {
        return "Default client name";
    }
}

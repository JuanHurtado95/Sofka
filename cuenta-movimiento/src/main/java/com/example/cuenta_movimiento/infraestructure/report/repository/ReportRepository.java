package com.example.cuenta_movimiento.infraestructure.report.repository;

import com.example.cuenta_movimiento.domain.account.entity.Account;
import com.example.cuenta_movimiento.domain.report.dto.ReportDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Account, Long> {

    @Query("SELECT new com.example.cuenta_movimiento.domain.report.dto.ReportDTO(" +
            "a.accountNumber, a.availableBalance, a.accountType, m.date, m.movementType, m.value) " +
            "FROM Account a " +
            "LEFT JOIN a.movements m " +
            "WHERE a.clientId = :clientId " +
            "AND (m.date BETWEEN :startDate AND :endDate OR m.date IS NULL) " +
            "ORDER BY a.accountNumber")
    List<ReportDTO> getReport(@Param("clientId") Long clientId,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate);
}
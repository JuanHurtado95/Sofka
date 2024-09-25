package com.example.cuenta_movimiento.domain.movement.entity;

import com.example.cuenta_movimiento.domain.account.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "movimientos")
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long movementId; // Clave primaria

    @Column(name = "fecha", nullable = false, columnDefinition = "DATETIME")
    private LocalDate date;

    @Column(name = "tipo_movimiento", nullable = false)
    private String movementType; // Ej: "DEPOSIT", "WITHDRAWAL"

    @Column(name = "valor", nullable = false)
    private Double value;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Account account; // Relación con la cuenta
}

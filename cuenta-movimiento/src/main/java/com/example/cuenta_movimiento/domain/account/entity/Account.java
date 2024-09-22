package com.example.cuenta_movimiento.domain.account.entity;

import com.example.cuenta_movimiento.domain.movement.entity.Movement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cuentas")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cuenta_id", nullable = false, columnDefinition = "BIGINT")
    private Long accountId; // Clave primaria

    @Column(name = "numero_cuenta", unique = true, nullable = false)
    private String accountNumber; // Número de cuenta único

    @Column(name = "tipo_cuenta", nullable = false)
    private String accountType; // Tipo de cuenta (corriente, ahorros, etc.)

    @Column(name = "saldo_inicial", nullable = false)
    private Double initialBalance; // Saldo inicial

    @Column(name = "estado")
    private Boolean status; // Estado de la cuenta

    @Column(name = "saldo_disponible", nullable = false)
    private Double availableBalance;

    @Column(name = "cliente_id", unique = true, nullable = false)
    private Long clientId; // ID del cliente asociado a la cuenta

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Movement> movements;
}
package com.example.cuenta_movimiento.domain.account.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    private String accountNumber; // Clave única
    private String accountType;
    private Double initialBalance;
    private Double availableBalance; // Saldo disponible
    private Long clientId; // ID del cliente asociado
    private boolean status;
}

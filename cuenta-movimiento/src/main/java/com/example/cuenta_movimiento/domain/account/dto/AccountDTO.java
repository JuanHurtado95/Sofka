package com.example.cuenta_movimiento.domain.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDTO {
    @NotBlank(message = "El número de cuenta es obligatorio")
    private String accountNumber; // Clave única

    @NotBlank(message = "El tipo de cuenta es obligatorio")
    @Pattern(regexp = "Ahorros|Corriente", message = "La cuenta debe ser 'Ahorros' o 'Corriente'")
    private String accountType;

    @NotNull(message = "El saldo inicial no puede ser nulo")
    @PositiveOrZero(message = "El saldo inicial debe ser mayor o igual a cero")
    private Double initialBalance;

    @NotNull(message = "El saldo disponible no puede ser nulo")
    @PositiveOrZero(message = "El saldo disponible debe ser mayor o igual a cero")
    private Double availableBalance;

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clientId;

    private boolean status;
}

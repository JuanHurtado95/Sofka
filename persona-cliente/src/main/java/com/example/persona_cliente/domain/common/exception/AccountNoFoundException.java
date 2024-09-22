package com.example.persona_cliente.domain.common.exception;

public class AccountNoFoundException extends RuntimeException {
    public AccountNoFoundException(String message) {
        super(message);
    }
}

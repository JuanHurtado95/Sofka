package com.example.persona_cliente.domain.common.exception;

public class CustomerIsAlreadyRegisterException extends RuntimeException{
    public CustomerIsAlreadyRegisterException(String message) {
        super(message);
    }
}
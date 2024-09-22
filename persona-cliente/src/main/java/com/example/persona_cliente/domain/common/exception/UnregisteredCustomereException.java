package com.example.persona_cliente.domain.common.exception;

public class UnregisteredCustomereException extends RuntimeException{
    public UnregisteredCustomereException(String message) {
        super(message);
    }
}

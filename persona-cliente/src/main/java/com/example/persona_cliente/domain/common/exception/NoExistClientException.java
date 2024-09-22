package com.example.persona_cliente.domain.common.exception;

public class NoExistClientException extends RuntimeException{
    public NoExistClientException(String message) {
        super(message);
    }
}

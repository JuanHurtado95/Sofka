package com.example.cuenta_movimiento.domain.common.exception;

public class AccountAlreadyExistsException extends RuntimeException{
    public AccountAlreadyExistsException(String message){ super(message);}
}

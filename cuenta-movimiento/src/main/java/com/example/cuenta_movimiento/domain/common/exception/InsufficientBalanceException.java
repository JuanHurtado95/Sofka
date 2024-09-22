package com.example.cuenta_movimiento.domain.common.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String message){ super(message);}
}

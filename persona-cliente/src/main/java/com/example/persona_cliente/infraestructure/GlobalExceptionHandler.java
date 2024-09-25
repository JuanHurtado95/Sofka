package com.example.persona_cliente.infraestructure;

import com.example.persona_cliente.domain.common.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UnregisteredCustomereException.class)
    public ResponseEntity<ErrorResponse> handleClienteInexistenteException(UnregisteredCustomereException ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(CustomerIsAlreadyRegisterException.class)
    public ResponseEntity<ErrorResponse> handleClienteYaRegistradoException(CustomerIsAlreadyRegisterException ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ErrorResponse> handleSaldoInsuficienteException(SaldoInsuficienteException ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AccountNoFoundException.class)
    public ResponseEntity<ErrorResponse> handleAccountNoFoundException(AccountNoFoundException ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(NoExistClientException.class)
    public ResponseEntity<ErrorResponse> handleNoExistClientException(NoExistClientException ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        String message = "Errores de validación: " + errors.toString();

        return buildResponseEntity(message, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(String message, HttpStatus httpStatus, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                message,
                request.getRequestURI()
        );
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
package com.example.cuenta_movimiento.infraestructure;

import com.example.cuenta_movimiento.domain.common.exception.AccountAlreadyExistsException;
import com.example.cuenta_movimiento.domain.common.exception.ErrorResponse;
import com.example.cuenta_movimiento.domain.common.exception.InsufficientBalanceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleAccountAlreadyExistsException(AccountAlreadyExistsException ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
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
        return buildResponseEntity("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR, request);
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

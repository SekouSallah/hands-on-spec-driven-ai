package com.ebanking.account.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now()).status(404).error("Not Found")
                .message(ex.getMessage()).path(req.getRequestURI()).code("ACCOUNT_NOT_FOUND").build());
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessRuleException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now()).status(400).error("Bad Request")
                .message(ex.getMessage()).path(req.getRequestURI()).code(ex.getErrorCode()).build());
    }

    @ExceptionHandler(IdleModeException.class)
    public ResponseEntity<ErrorResponse> handleIdle(IdleModeException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now()).status(503).error("Service Unavailable")
                .message(ex.getMessage()).path(req.getRequestURI()).code("IDLE_MODE_ACTIVE").build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String msg = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now()).status(400).error("Validation Error")
                .message(msg).path(req.getRequestURI()).code("ACCOUNT_VALIDATION_ERROR").build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now()).status(500).error("Internal Server Error")
                .message("An unexpected error occurred").path(req.getRequestURI()).code("INTERNAL_ERROR").build());
    }
}

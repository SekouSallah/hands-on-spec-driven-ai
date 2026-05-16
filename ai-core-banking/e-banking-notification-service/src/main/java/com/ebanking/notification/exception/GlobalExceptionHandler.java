package com.ebanking.notification.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(404).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now()).status(404).error("Not Found")
                .message(ex.getMessage()).path(req.getRequestURI()).code("NOT_FOUND").build());
    }
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessRuleException ex, HttpServletRequest req) {
        return ResponseEntity.status(400).body(ErrorResponse.builder()
                .timestamp(LocalDateTime.now()).status(400).error("Bad Request")
                .message(ex.getMessage()).path(req.getRequestURI()).code(ex.getErrorCode()).build());
    }
}

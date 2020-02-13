package com.ecommerce.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        return ResponseEntity.ok(new Error("CODE_TO_BE_DEFINED", ex.getMessage()));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity handleException(DatabaseException ex) {
        return ResponseEntity.ok(new Error(ex.getCode(), ex.getMessage()));
    }
}

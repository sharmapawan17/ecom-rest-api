package com.ecommerce.exception;

import com.ecommerce.ResponseWithStatus;
import com.ecommerce.Status;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        return ResponseEntity.ok(new ResponseWithStatus(new Status("failure", "Failed with error"),
                ex.getMessage()));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity handleException(DatabaseException ex) {
        return ResponseEntity.ok(new Error(ex.getCode(), ex.getMessage()));
    }
}

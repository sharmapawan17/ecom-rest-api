package com.ecommerce.exception;

import com.ecommerce.ResponseWithStatus;
import com.ecommerce.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ApiExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        log.error(ex.getMessage());
        return ResponseEntity.ok(new ResponseWithStatus(new Status(false, "Failed with error"),
                ex.getMessage()));
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity handleException(DatabaseException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.ok(new Error(ex.getCode(), ex.getMessage()));
    }
}

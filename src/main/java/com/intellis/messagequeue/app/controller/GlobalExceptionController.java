package com.intellis.messagequeue.app.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Some parameters are invalid")
    @ExceptionHandler(IllegalArgumentException.class)
    public void handleIllegalArgumentException(IllegalArgumentException e) {
        log.info("IllegalArgumentException: ", e);
    }

    @ResponseStatus(code = HttpStatus.CONFLICT, reason = "Database connection problem")
    @ExceptionHandler(SQLException.class)
    public void handleSQLException(SQLException e) {
        log.info("SQLException: ", e);
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity not found")
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(EntityNotFoundException e) {
        log.info("EntityNotFoundException: ", e);
    }

    @ExceptionHandler(ExhaustedRetryException.class)
    public ResponseEntity<String> handleMethodExhaustedRetryException(ExhaustedRetryException e) {
        log.info("ExhaustedRetryException: ", e);
        return ResponseEntity.badRequest().body("Incomplete entity model");
    }
}

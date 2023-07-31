package org.programmers.jpaweeklymission.global;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Collections;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MESSAGE = "message";

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleEntityNotFoundException(EntityNotFoundException e) {
        log.warn(e.getMessage(), e);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.warn(e.getMessage(), e);
        return ResponseEntity.badRequest().body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(Collections.singletonMap(MESSAGE, e.getMessage()));
    }
}

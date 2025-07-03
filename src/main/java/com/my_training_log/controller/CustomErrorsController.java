package com.my_training_log.controller;
import com.my_training_log.exception.NotFoundException;
import jakarta.transaction.TransactionalException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomErrorsController {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException ex) {
        String message = "Value not found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        List<String> errorsList = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsList);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleJpaValidationException(ConstraintViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        List<String> errorsList = ex.getConstraintViolations().stream()
                .map(cv -> cv.getPropertyPath() + ": " + cv.getMessage())
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorsList);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity handleIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        String error = ex.getRootCause() != null ?
                "Database constraint violation: " + ex.getRootCause().getMessage() :
                "Data integrity error occurred.";

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


}

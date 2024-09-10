package com.example.topups.infrastructure.config.exception;

import com.example.topups.infrastructure.controllers.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("Exception MethodArgumentNotValidException: cause:{}", ex.getCause());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError) {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            } else {
                String objectName = error.getObjectName();
                String errorMessage = error.getDefaultMessage();
                errors.put(objectName, errorMessage);
            }
        });

        ResponseDTO<Map<String, String>> response = new ResponseDTO<>(
                false,
                HttpStatus.BAD_REQUEST.value(),
                "Error en los datos enviados",
                errors
        );

        logger.error("Exception MethodArgumentNotValidException: code:{}, message:{}, cause:{}", HttpStatus.BAD_REQUEST.value(), errors, ex.getCause());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}

package com.sdg.countryapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CountryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCountriesNotFoundException(CountryNotFoundException ex) {

        Map<String, String> errors = new HashMap<>();

            errors.put("Info error", String.valueOf(HttpStatus.NOT_FOUND.value()+ " "+HttpStatus.NOT_FOUND.getReasonPhrase()));
            errors.put("message", ex.getMessage());


        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    // Manejo de excepciones de validación - Método POST
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put("Info error", String.valueOf(HttpStatus.BAD_REQUEST.value()+ " "+HttpStatus.BAD_REQUEST.getReasonPhrase()));
            errors.put("message", error.getDefaultMessage());

        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("Info error", String.valueOf(HttpStatus.BAD_REQUEST.value()+ " "+HttpStatus.BAD_REQUEST.getReasonPhrase()));
        errorResponse.put("message", "Population debe ser un número");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    // Manejo de otras excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("Ha ocurrido un error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

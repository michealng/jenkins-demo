package com.example.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler({ ConstraintViolationException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationError(ConstraintViolationException exception) {
        Map<String, Object> response = new HashMap<>();
        List<Map<String, String>> errors = new ArrayList<>();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            Map<String, String> transformedError = new HashMap<>();

            String fieldName = violation.getPropertyPath().toString();
            transformedError.put("field", fieldName.substring(fieldName.lastIndexOf('.') + 1));
            transformedError.put("error", violation.getMessage());

            errors.add(transformedError);
        }
        response.put("message", "Bad request");
        response.put("errors", errors);
        return response;
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleValidationError(MethodArgumentNotValidException exception) {
        Map<String, Object> response = new HashMap<>();

        if (exception.hasFieldErrors()) {
            List<Map<String, String>> errors = new ArrayList<>();

            for (FieldError error : exception.getFieldErrors()) {
                Map<String, String> transformedError = new HashMap<>();
                transformedError.put("field", error.getField());
                transformedError.put("error", error.getDefaultMessage());

                errors.add(transformedError);
            }
            response.put("errors", errors);
        }

        return response;
    }
}

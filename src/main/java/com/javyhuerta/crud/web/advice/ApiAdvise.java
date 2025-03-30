package com.javyhuerta.crud.web.advice;

import java.util.HashMap;
import java.util.Map;

import com.javyhuerta.crud.exception.ApiException;
import com.javyhuerta.crud.web.util.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiAdvise {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ResponseMessage<Void>> getException(ApiException exception) {

        ResponseMessage<Void> msg =
                ResponseMessage.<Void>builder().message(exception.getMessage()).build();

        return new ResponseEntity<>(msg, exception.getHttpStatus());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(
                        error -> {
                            String fieldName = ((FieldError) error).getField();
                            String errorMessage = error.getDefaultMessage();
                            errors.put(fieldName, errorMessage);
                        });
        return errors;
    }
}


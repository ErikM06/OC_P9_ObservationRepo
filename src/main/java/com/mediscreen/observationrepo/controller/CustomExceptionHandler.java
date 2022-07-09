package com.mediscreen.observationrepo.controller;

import com.mediscreen.observationrepo.customExceptions.IdNotFoundException;
import com.mediscreen.observationrepo.customExceptions.PatIdNotFoundException;
import com.mediscreen.observationrepo.model.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final String INCORRECT_REQUEST = "INCORRECT_REQUEST";

    @ExceptionHandler (value = IdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException (IdNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler (value = PatIdNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException (PatIdNotFoundException ex){
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ErrorResponse error = new ErrorResponse(INCORRECT_REQUEST, details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}

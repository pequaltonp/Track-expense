package com.example.demo.rest;

import com.example.demo.exception.ExpensesErrorResponse;
import com.example.demo.exception.ExpensesNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExpensesExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExpensesErrorResponse> handleExpensesNotFound(
            ExpensesNotFoundException expensesNotFoundException) {
        return new ResponseEntity<>(
                new ExpensesErrorResponse(HttpStatus.NOT_FOUND.value(),
                        expensesNotFoundException.getMessage(),
                        System.currentTimeMillis()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExpensesErrorResponse> handleGenericExceptions(Exception exception) {
        return new ResponseEntity<>(new ExpensesErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exception.getMessage(),
                System.currentTimeMillis()), HttpStatus.BAD_REQUEST);
    }
}

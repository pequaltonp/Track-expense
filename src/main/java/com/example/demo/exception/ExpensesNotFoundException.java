package com.example.demo.exception;

public class ExpensesNotFoundException extends RuntimeException {
    public ExpensesNotFoundException(String message) {
        super(message);
    }

}

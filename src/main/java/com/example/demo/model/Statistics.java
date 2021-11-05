package com.example.demo.model;

import lombok.*;

import java.time.LocalDate;

/**
 * Projection type for representation aggregation sql methods
 */
public interface Statistics {
    LocalDate getDate();
    Double getMaxExpense();
    Double getSumOfExpenses();
    Double getAvgExpense();
}

package com.example.demo.rest;

import com.example.demo.exception.ExpensesNotFoundException;
import com.example.demo.model.Expenses;
import com.example.demo.model.Statistics;
import com.example.demo.service.ExpensesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesRestController {
    @Autowired
    private ExpensesServiceImpl expensesService;

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public void saveExpense(@Valid @RequestBody Expenses expenses) {
        expensesService.saveExepenses(expenses);
    }

    @PutMapping("/{date:\\d{4}-\\d{2}-\\d{2}}/{type}/{expense}")
    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateExpense(@PathVariable String date,
                              @PathVariable String type,
                              @PathVariable double expense){
        expensesService.setExpenseByDateAndType(expense, LocalDate.parse(date), type);
    }

    @DeleteMapping("/{date:\\d{4}-\\d{2}-\\d{2}}/{type}")
    @Transactional
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteExpense(@PathVariable String date,
                              @PathVariable String type) {
        expensesService.deleteExpense(LocalDate.parse(date), type);
    }

    @GetMapping("/date/{date:\\d{4}-\\d{2}-\\d{2}}")
    public List<Expenses> getExpensesAtDate(@PathVariable String date) {

        List<Expenses> expenses = expensesService.getExpensesDay(LocalDate.parse(date));

        if (expenses == null || expenses.size() == 0)
            throw new ExpensesNotFoundException(
                    String.format("expense at this date: %s doesn't exist", date));

            return expenses;
    }

    @GetMapping("/type/{type}")
    public List<Expenses> getExpensesType(@PathVariable String type) {
        List<Expenses> expenses = expensesService.getExpensesByType(type);

        if (expenses == null || expenses.size() == 0)
            throw new ExpensesNotFoundException(
                    String.format("expenses by type = %s doesn't exist", type));

        return expenses;
    }

    @GetMapping("/dates/{from:\\d{4}-\\d{2}-\\d{2}}/{before:\\d{4}-\\d{2}-\\d{2}}")
    public List<Expenses> getExpensesBetween(@PathVariable String from,
                                            @PathVariable String before) {
        List<Expenses> expenses = expensesService
                .getExpensesBetweenDates(LocalDate.parse(from), LocalDate.parse(before));

        if (expenses == null || expenses.size() == 0)
            throw new ExpensesNotFoundException(
                    String.format("expense between %s and %s doesn't exist", from, before));

        return expenses;
    }

    @GetMapping("/statistics/{date:\\d{4}-\\d{2}-\\d{2}}")
    public Statistics getStatisticsAtDay(@PathVariable String date) {
        Statistics statistics = expensesService.getTotalExpenseAtDay(LocalDate.parse(date));

        if (statistics == null)
            throw new ExpensesNotFoundException(
                    String.format("expense at this date: %s doesn't exist", date));

        return statistics;
    }

    @GetMapping("/statistics/{from:\\d{4}-\\d{2}-\\d{2}}/{before:\\d{4}-\\d{2}-\\d{2}}")
    public List<Statistics> getStatisticsBetweenDates(@PathVariable String from,
                                                @PathVariable String before) {
        List<Statistics> statistics = expensesService
                .getTotalExpenseBetweenDates(LocalDate.parse(from), LocalDate.parse(before));

        if (statistics == null || statistics.size() == 0)
            throw new ExpensesNotFoundException(
                    String.format("expense between %s and %s doesn't exist", from, before));

        return statistics;
    }
}

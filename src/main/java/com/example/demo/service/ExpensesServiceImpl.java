package com.example.demo.service;

import com.example.demo.model.ExpenseType;
import com.example.demo.model.Expenses;
import com.example.demo.model.Statistics;
import com.example.demo.repository.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpensesServiceImpl implements ExpensesService {
    @Autowired
    private ExpensesRepository expensesRepository;

    private ExpenseType defineExpenseType(String type) {
        ExpenseType expenseType = ExpenseType.house;

        switch (type) {
            case "food":
                expenseType = ExpenseType.food;
                break;
            case "transport":
                expenseType = ExpenseType.transport;
                break;
            case "hygiene":
                expenseType = ExpenseType.hygiene;
                break;
            case "medicine":
                expenseType = ExpenseType.medicine;
                break;
            case "rent":
                expenseType = ExpenseType.rent;
                break;
            case "education":
                expenseType = ExpenseType.education;
                break;
            case "credit":
                expenseType = ExpenseType.credit;
                break;
        }

        return expenseType;
    }

    public void saveExepenses(Expenses expenses) {
        expensesRepository.save(expenses);
    }

    public void deleteExpense(LocalDate date, String type) {
        expensesRepository.deleteByDateAndType(date, defineExpenseType(type));
    }

    public void setExpenseByDateAndType(double expense, LocalDate date, String type) {
        expensesRepository.setMoneyByDateAndType(expense, date, defineExpenseType(type));
    }

    public List<Expenses> getExpensesDay(LocalDate date) {
        Sort.TypedSort<Expenses> expensesTypedSort = Sort.sort(Expenses.class);
        Sort sort = expensesTypedSort.by(Expenses::getExpense).descending();

        return expensesRepository.findExpensesByDate(date, sort);
    }

    public List<Expenses> getExpensesByType(String type) {
        Sort.TypedSort<Expenses> expensesTypedSort = Sort.sort(Expenses.class);
        Sort sort = expensesTypedSort.by(Expenses::getExpense).descending();

        return expensesRepository.findExpensesByType(defineExpenseType(type), sort);
    }

    public List<Expenses> getExpensesBetweenDates(LocalDate date, LocalDate date2) {
        return expensesRepository.findExpensesByDateBetween(date, date2);
    }

    // get statistics
    public List<Statistics> getTotalExpenseBetweenDates(LocalDate date1, LocalDate date2) {
        return expensesRepository.getStatisticsByDateBetween(date1, date2);
    }

    public Statistics getTotalExpenseAtDay(LocalDate date) {
        return expensesRepository.getStatisticsByDate(date);
    }

}

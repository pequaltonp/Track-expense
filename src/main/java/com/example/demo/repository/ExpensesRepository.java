package com.example.demo.repository;

import com.example.demo.model.ExpenseType;
import com.example.demo.model.Expenses;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import com.example.demo.model.Statistics;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {

    @Query(value = "select e.expenses_date as date, max(e.expense) as maxExpense," +
            "sum(e.expense) as sumOfExpenses, avg(e.expense) as avgExpense" +
            " from Expenses e " +
            "where e.expenses_date = ?1" +
            " group by e.expenses_date", nativeQuery = true)
    Statistics getStatisticsByDate(LocalDate date);

    @Query(value = "select e.expenses_date as date, max(e.expense) as maxExpense," +
            "sum(e.expense) as sumOfExpenses, avg(e.expense) as avgExpense" +
            " from Expenses e " +
            " where e.expenses_date between ?1 and ?2" +
            " group by e.expenses_date", nativeQuery = true)
    List<Statistics> getStatisticsByDateBetween(LocalDate from, LocalDate before);

    // find and sort by property
    List<Expenses> findExpensesByDate(LocalDate date, Sort sort);
    List<Expenses> findExpensesByDateBetween(LocalDate date, LocalDate date2);
    List<Expenses> findExpensesByType(ExpenseType type, Sort sort);

    @Modifying
    @Query("delete from Expenses e where e.date = ?1 and e.type = ?2")
    void deleteByDateAndType(LocalDate date, ExpenseType type);

    @Modifying
    @Query("update Expenses e set e.expense = ?1 where e.date = ?2 and e.type = ?3")
    void setMoneyByDateAndType(double expense, LocalDate date, ExpenseType type);

}

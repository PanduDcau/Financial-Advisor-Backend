package com.example.authdemo.service;

import com.example.authdemo.dto.ExpenseRequest;
import com.example.authdemo.model.Expense;
import com.example.authdemo.model.User;
import com.example.authdemo.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public Expense addExpense(User user, ExpenseRequest request) {
        Expense expense = new Expense();
        expense.setUser(user);
        expense.setAmount(request.amount());
        expense.setCategory(request.category());
        expense.setDate(request.date() != null ? request.date() : new Date());
        expense.setDescription(request.description());
        return expenseRepository.save(expense);
    }

    public List<Expense> getUserExpenses(User user) {
        return expenseRepository.findByUser(user);
    }

    public List<Expense> getExpensesByCategory(User user, String category) {
        return expenseRepository.findByUserAndCategory(user, category);
    }
}
package com.example.authdemo.repository;

import com.example.authdemo.model.Expense;
import com.example.authdemo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface ExpenseRepository extends MongoRepository<Expense, String> {
    List<Expense> findByUser(User user);
    List<Expense> findByUserAndCategory(User user, String category);
}
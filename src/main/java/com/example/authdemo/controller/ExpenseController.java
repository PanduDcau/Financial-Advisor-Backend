package com.example.authdemo.controller;

import com.example.authdemo.dto.ExpenseRequest;
import com.example.authdemo.model.User;
import com.example.authdemo.repository.UserRepository;
import com.example.authdemo.security.UserDetailsImpl;
import com.example.authdemo.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    public ExpenseController(ExpenseService expenseService,
                             UserRepository userRepository) {
        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }

    private User getAuthenticatedUser(UserDetailsImpl userDetails) {
        return userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public ResponseEntity<?> addExpense(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody ExpenseRequest request) {
        User user = getAuthenticatedUser(userDetails);
        return ResponseEntity.ok(expenseService.addExpense(user, request));
    }

    @GetMapping
    public ResponseEntity<?> getUserExpenses(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = getAuthenticatedUser(userDetails);
        return ResponseEntity.ok(expenseService.getUserExpenses(user));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getExpensesByCategory(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable String category) {
        User user = getAuthenticatedUser(userDetails);
        return ResponseEntity.ok(expenseService.getExpensesByCategory(user, category));
    }
}
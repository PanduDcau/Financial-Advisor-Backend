package com.example.authdemo.dto;

import com.example.authdemo.model.Expense;
import jakarta.validation.constraints.*;
import java.util.Date;

public record ExpenseRequest(
        @NotNull @Positive Double amount,
        Date date,
        String description
) {}
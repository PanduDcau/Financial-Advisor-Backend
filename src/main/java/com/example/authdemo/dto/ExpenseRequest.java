package com.example.authdemo.dto;

import com.example.authdemo.model.Expense;
import jakarta.validation.constraints.*;
import java.util.Date;

public record ExpenseRequest(
        @NotNull @Positive Double amount,
        @NotBlank String category,
        Date date,
        String description
) {}
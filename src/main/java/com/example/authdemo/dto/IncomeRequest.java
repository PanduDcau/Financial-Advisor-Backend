package com.example.authdemo.dto;

import java.time.LocalDate;

public record IncomeRequest(
        Double value,
        String month
) {}

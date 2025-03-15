package com.example.authdemo.model;

import lombok.Data;

@Data
public class Income {
    private String month;
    private double value;

    public Income(String month, double value) {
        this.month = month;
        this.value = value;
    }
}

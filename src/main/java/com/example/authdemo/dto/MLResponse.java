package com.example.authdemo.dto;

public class MLResponse {
    private String category;
    private double confidence;

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }
}
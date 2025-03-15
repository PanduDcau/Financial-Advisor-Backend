package com.example.authdemo.service;

import com.example.authdemo.dto.ExpenseRequest;
import com.example.authdemo.dto.MLResponse;
import com.example.authdemo.model.Expense;
import com.example.authdemo.model.User;
import com.example.authdemo.repository.ExpenseRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Calendar;
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
        expense.setDate(request.date() != null ? request.date() : new Date());
        expense.setDescription(request.description());
        String mlCategory = getCategoryFromMLModel(request.description());
        expense.setCategory(mlCategory);
        return expenseRepository.save(expense);
    }

    public static String getCurrentMonth() {
        return LocalDate.now().getYear() + "-" +
                String.format("%02d", LocalDate.now().getMonthValue());
    }

    public List<Expense> getCurrentMonthExpenses(User user) {
        String currentMonth = getCurrentMonth();
        return expenseRepository.findByUserAndMonth(user, currentMonth);
    }

    public static String getMonthFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1; // Months are 0-based in Calendar
        return year + "-" + String.format("%02d", month);
    }

    private String getCategoryFromMLModel(String description) {
        String url = "http://127.0.0.1:5000/predict";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("product_name", description);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<MLResponse> response = restTemplate.postForEntity(
                    url,
                    requestEntity,
                    MLResponse.class
            );

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getCategory().toUpperCase();
            }
        } catch (RestClientException e) {
            System.out.println(e.toString());
        }

        return "OTHER";
    }

    public List<Expense> getUserExpenses(User user) {
        return expenseRepository.findByUser(user);
    }

    public List<Expense> getExpensesByCategory(User user, String category) {
        return expenseRepository.findByUserAndCategory(user, category);
    }
}
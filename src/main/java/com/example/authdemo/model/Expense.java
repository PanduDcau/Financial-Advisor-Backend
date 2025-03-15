package com.example.authdemo.model;

import com.example.authdemo.service.ExpenseService;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Data
@Document(collection = "expenses")
public class Expense {
    @Id
    private String id;

    @DBRef
    private User user;
    private Double amount;
    private String category;
    private Date date;
    private String description;
    private String month;

    public void setDate(Date date) {
        this.date = date;
        this.month = ExpenseService.getMonthFromDate(date);
    }
}
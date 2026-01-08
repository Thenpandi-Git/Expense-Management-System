package com.ExpenseTracker.Entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ExpenseTracker.Enum.ExpenseCategory;
import com.ExpenseTracker.Enum.ExpenseType;

@Document(collection = "expenses")
public class Expense {

    @Id
    private String id;

    private String userId;
    private ExpenseCategory category;
    private Double amount;
    private LocalDate date;
    private String paymentMode;
    private String description;
    private ExpenseType type;


	public Expense(String userId, ExpenseCategory category, Double amount,
                   LocalDate date, String paymentMode, String description,ExpenseType type) {
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.date = date;
        this.paymentMode = paymentMode;
        this.description = description;
        this.type = type;
        


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
		this.category = category;
	}

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public ExpenseType getType() {
		return type;
	}

	public void setType(ExpenseType type) {
		this.type = type;
	}
    
   
	
}

package com.ExpenseTracker.Service;



import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ExpenseTracker.Entity.Expense;
import com.ExpenseTracker.Enum.ExpenseType;
import com.ExpenseTracker.Repository.ExpenseRepository;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    
    public Expense addIncome(Expense income) {

        income.setType(ExpenseType.INCOME);

        if (income.getDate() == null) {
            income.setDate(LocalDate.now());
        }

        return expenseRepository.save(income);
    }
    

    public Expense addExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(String id, Expense expense) {
        Expense existing = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        existing.setUserId(expense.getUserId());
        existing.setCategory(expense.getCategory());
        existing.setAmount(expense.getAmount());
        existing.setDate(expense.getDate());
        existing.setPaymentMode(expense.getPaymentMode());
        existing.setDescription(expense.getDescription());
        existing.setType(expense.getType());

        return expenseRepository.save(existing);
    }

    
    public void deleteExpense(String id) {
        expenseRepository.deleteById(id);
    }

  
    public Expense getExpenseById(String id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
    }

  
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    

    
    public Page<Expense> getFilteredExpenses(
            String category,
            String paymentMode,
            LocalDate date,
            Pageable pageable) {

        if (category != null && paymentMode != null && date != null) {
            return expenseRepository
                    .findByCategoryAndPaymentModeAndDate(
                            category, paymentMode, date, pageable);
        }
        else if (category != null && date != null) {
            return expenseRepository
                    .findByCategoryAndDate(category, date, pageable);
        }
        else if (paymentMode != null && date != null) {
            return expenseRepository
                    .findByPaymentModeAndDate(paymentMode, date, pageable);
        }
        else if (date != null) {
            return expenseRepository.findByDate(date, pageable);
        }
        else if (category != null) {
            return expenseRepository.findByCategory(category, pageable);
        }
        else if (paymentMode != null) {
            return expenseRepository.findByPaymentMode(paymentMode, pageable);
        }

        return expenseRepository.findAll(pageable);
    }

    
    
}

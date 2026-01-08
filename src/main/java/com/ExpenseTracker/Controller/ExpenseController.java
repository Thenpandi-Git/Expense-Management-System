package com.ExpenseTracker.Controller;


import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import com.ExpenseTracker.Entity.Expense;
import com.ExpenseTracker.Service.ExpenseReportService;
import com.ExpenseTracker.Service.ExpenseService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseReportService reportService;
    
    
    @PostMapping("/income")
    public Expense createIncome(@RequestBody Expense income) {
        return expenseService.addIncome(income);
    }

    // ===================== CRUD APIs =====================

    // Create Expense
    @PostMapping("/expenses")
    public Expense addExpense(@RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    // Read Expense by ID
    @GetMapping("/expenses/{id}")
    public Expense getExpense(@PathVariable String id) {
        return expenseService.getExpenseById(id);
    }

    // Update Expense
    @PutMapping("/expenses/{id}")
    public Expense updateExpense(@PathVariable String id, @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    // Delete Expense
    @DeleteMapping("/expenses/{id}")
    public void deleteExpense(@PathVariable String id) {
        expenseService.deleteExpense(id);
    }

    // ================= Pagination & Filtering =================

    @GetMapping("/expenses")
    public Page<Expense> listExpenses(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String paymentMode,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate date
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return expenseService.getFilteredExpenses(category, paymentMode,date, pageable);
    }

    // ===================== REPORT / AGGREGATION APIs =====================

    // Category Summary
    @GetMapping("/reports/summary/category")
    public List<Document> categorySummary(@RequestParam String userId) {
        return reportService.getCategorySummary(userId);
    }

    // Payment Mode Summary
    @GetMapping("/reports/summary/payment-mode")
    public List<Document> paymentModeSummary(@RequestParam String userId) {
        return reportService.getPaymentModeSummary(userId);
    }

    // Monthly Summary
    @GetMapping("/reports/summary/monthly")
    public List<Document> monthlySummary(@RequestParam String userId) {
        return reportService.getMonthlySummary(userId);
    }

    // Top Categories
    @GetMapping("/reports/top-categories")
    public List<Document> topCategories(
            @RequestParam String userId,
            @RequestParam(defaultValue = "5") int limit) {
        return reportService.getTopCategories(userId, limit);
    }

    // Daily Spending Trend
    @GetMapping("/reports/trend/daily")
    public List<Document> dailyTrend(@RequestParam String userId) {
        return reportService.getDailyTrend(userId);
    }

    // Income vs Expense Summary
    @GetMapping("/reports/income-expense")
    public Document incomeExpense(@RequestParam String userId) {
        return reportService.getIncomeExpenseSummary(userId);
    }
}

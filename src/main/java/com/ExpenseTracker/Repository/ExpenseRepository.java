package com.ExpenseTracker.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.ExpenseTracker.Entity.Expense;

public interface ExpenseRepository extends MongoRepository<Expense, String> {

    List<Expense> findByUserId(String userId);

    List<Expense> findByUserIdAndDateBetween(
            String userId, LocalDate startDate, LocalDate endDate);

	Page<Expense> findByCategoryAndPaymentMode(String category, String paymentMode, Pageable pageable);

	Page<Expense> findByCategory(String category, Pageable pageable);

	Page<Expense> findByPaymentMode(String paymentMode, Pageable pageable);

	Page<Expense> findByPaymentModeAndDate(String paymentMode, LocalDate date, Pageable pageable);

	Page<Expense> findByDate(LocalDate date, Pageable pageable);

	Page<Expense> findByCategoryAndDate(String category, LocalDate date, Pageable pageable);

	Page<Expense> findByCategoryAndPaymentModeAndDate(String category, String paymentMode, LocalDate date,
			Pageable pageable);
}

package com.expensetracker.expense_tracker.service;

import com.expensetracker.expense_tracker.dto.ExpenseRequest;
import com.expensetracker.expense_tracker.dto.ExpenseResponse;
import com.expensetracker.expense_tracker.dto.ExpenseSummary;
import com.expensetracker.expense_tracker.entity.Expense;
import com.expensetracker.expense_tracker.exception.ExpenseNotFoundException;
import com.expensetracker.expense_tracker.exception.InvalidExpenseException;
import com.expensetracker.expense_tracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for expense management.
 * This class contains business logic for CRUD operations, validation,
 * and data transformations between entities and DTOs.
 */
@Service
@Transactional
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    /**
     * Constructor injection for ExpenseRepository
     * Spring automatically injects the repository bean
     *
     * @param expenseRepository The repository for database operations
     */
    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    /**
     * Retrieves all expenses from the database
     *
     * @return List of all expenses as ExpenseResponse DTOs
     */
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(ExpenseResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a single expense by its ID
     *
     * @param id The ID of the expense to retrieve
     * @return The expense as ExpenseResponse DTO
     * @throws ExpenseNotFoundException if no expense exists with the given ID
     */
    @Transactional(readOnly = true)
    public ExpenseResponse getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));
        return ExpenseResponse.fromEntity(expense);
    }

    /**
     * Creates a new expense in the database
     *
     * @param request The expense data to create
     * @return The created expense as ExpenseResponse DTO
     * @throws InvalidExpenseException if the request data is invalid
     */
    public ExpenseResponse createExpense(ExpenseRequest request) {
        // Additional business validation (beyond @Valid annotations)
        validateExpenseRequest(request);

        // Convert DTO to entity
        Expense expense = new Expense();
        expense.setTransactionDate(request.getTransactionDate());
        expense.setName(request.getName());
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setCategory(request.getCategory());
        expense.setTag(request.getTag());

        // Save to database (audit fields set by @PrePersist)
        Expense savedExpense = expenseRepository.save(expense);

        return ExpenseResponse.fromEntity(savedExpense);
    }

    /**
     * Updates an existing expense
     *
     * @param id      The ID of the expense to update
     * @param request The updated expense data
     * @return The updated expense as ExpenseResponse DTO
     * @throws ExpenseNotFoundException if no expense exists with the given ID
     * @throws InvalidExpenseException  if the request data is invalid
     */
    public ExpenseResponse updateExpense(Long id, ExpenseRequest request) {
        // Check if expense exists
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(id));

        // Additional business validation
        validateExpenseRequest(request);

        // Update fields
        expense.setTransactionDate(request.getTransactionDate());
        expense.setName(request.getName());
        expense.setAmount(request.getAmount());
        expense.setDescription(request.getDescription());
        expense.setCategory(request.getCategory());
        expense.setTag(request.getTag());

        // Save changes (updatedAt set by @PreUpdate)
        Expense updatedExpense = expenseRepository.save(expense);

        return ExpenseResponse.fromEntity(updatedExpense);
    }

    /**
     * Deletes an expense by its ID
     *
     * @param id The ID of the expense to delete
     * @throws ExpenseNotFoundException if no expense exists with the given ID
     */
    public void deleteExpense(Long id) {
        // Check if expense exists before attempting delete
        if (!expenseRepository.existsById(id)) {
            throw new ExpenseNotFoundException(id);
        }

        expenseRepository.deleteById(id);
    }

    /**
     * Searches for expenses by name (case-insensitive partial match)
     *
     * @param keyword The search keyword
     * @return List of matching expenses as ExpenseResponse DTOs
     */
    @Transactional(readOnly = true)
    public List<ExpenseResponse> searchExpenses(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllExpenses();
        }

        return expenseRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(ExpenseResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves expenses by category
     *
     * @param category The category to filter by
     * @return List of expenses in the specified category
     */
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category)
                .stream()
                .map(ExpenseResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves expenses within a date range
     *
     * @param startDate The start of the date range (inclusive)
     * @param endDate   The end of the date range (inclusive)
     * @return List of expenses in the date range
     */
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return expenseRepository.findByTransactionDateBetween(startDate, endDate)
                .stream()
                .map(ExpenseResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves expenses above a certain amount
     *
     * @param amount The minimum amount threshold
     * @return List of expenses above the specified amount
     */
    @Transactional(readOnly = true)
    public List<ExpenseResponse> getExpensesAboveAmount(BigDecimal amount) {
        return expenseRepository.findByAmountGreaterThan(amount)
                .stream()
                .map(ExpenseResponse::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Gets the total amount spent per category
     *
     * @return List of expense summaries grouped by category
     */
    @Transactional(readOnly = true)
    public List<ExpenseSummary> getTotalByCategory() {
        List<Object[]> results = expenseRepository.getTotalByCategory();
        return results.stream()
                .map(result -> new ExpenseSummary(
                        (String) result[0], // category
                        (BigDecimal) result[1] // total amount
                ))
                .collect(Collectors.toList());
    }

    /**
     * Validates expense request data with additional business rules
     *
     * @param request The request to validate
     * @throws InvalidExpenseException if validation fails
     */
    private void validateExpenseRequest(ExpenseRequest request) {
        // Check if name is meaningful (not just whitespace)
        if (request.getName() != null && request.getName().trim().isEmpty()) {
            throw new InvalidExpenseException("Name cannot be empty or just whitespace");
        }

        // Check if category is meaningful
        if (request.getCategory() != null && request.getCategory().trim().isEmpty()) {
            throw new InvalidExpenseException("Category cannot be empty or just whitespace");
        }

        // Additional validation: amount should have at most 2 decimal places
        if (request.getAmount() != null && request.getAmount().scale() > 2) {
            throw new InvalidExpenseException("Amount cannot have more than 2 decimal places");
        }
    }
}

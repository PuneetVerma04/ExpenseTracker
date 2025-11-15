package com.expensetracker.expense_tracker.controller;

import com.expensetracker.expense_tracker.dto.ExpenseRequest;
import com.expensetracker.expense_tracker.dto.ExpenseResponse;
import com.expensetracker.expense_tracker.dto.ExpenseSummary;
import com.expensetracker.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Controller for expense management API.
 * Provides endpoints for CRUD operations and queries on expenses.
 * All endpoints are under /api/expenses base path.
 */
@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*") // Allow cross-origin requests (adjust in production)
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * Constructor injection for ExpenseService
     *
     * @param expenseService The service handling business logic
     */
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * GET /api/expenses - Retrieves all expenses
     * Optionally filters by category or search keyword
     *
     * @param category Optional category filter
     * @param search   Optional search keyword (searches in description)
     * @return List of expenses matching the criteria
     */
    @GetMapping
    public ResponseEntity<List<ExpenseResponse>> getAllExpenses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search) {

        List<ExpenseResponse> expenses;

        if (search != null && !search.isEmpty()) {
            // Search by description
            expenses = expenseService.searchExpenses(search);
        } else if (category != null && !category.isEmpty()) {
            // Filter by category
            expenses = expenseService.getExpensesByCategory(category);
        } else {
            // Get all expenses
            expenses = expenseService.getAllExpenses();
        }

        return ResponseEntity.ok(expenses);
    }

    /**
     * GET /api/expenses/{id} - Retrieves a single expense by ID
     *
     * @param id The ID of the expense to retrieve
     * @return The expense with the specified ID
     * @throws com.expensetracker.expense_tracker.exception.ExpenseNotFoundException if
     *                                                                               not
     *                                                                               found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponse> getExpenseById(@PathVariable Long id) {
        ExpenseResponse expense = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expense);
    }

    /**
     * POST /api/expenses - Creates a new expense
     *
     * @param request The expense data (validated against ExpenseRequest
     *                constraints)
     * @return The created expense with 201 Created status
     */
    @PostMapping
    public ResponseEntity<ExpenseResponse> createExpense(@Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse createdExpense = expenseService.createExpense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExpense);
    }

    /**
     * PUT /api/expenses/{id} - Updates an existing expense
     *
     * @param id      The ID of the expense to update
     * @param request The updated expense data (validated)
     * @return The updated expense
     * @throws com.expensetracker.expense_tracker.exception.ExpenseNotFoundException if
     *                                                                               not
     *                                                                               found
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseRequest request) {

        ExpenseResponse updatedExpense = expenseService.updateExpense(id, request);
        return ResponseEntity.ok(updatedExpense);
    }

    /**
     * DELETE /api/expenses/{id} - Deletes an expense
     *
     * @param id The ID of the expense to delete
     * @return 204 No Content on successful deletion
     * @throws com.expensetracker.expense_tracker.exception.ExpenseNotFoundException if
     *                                                                               not
     *                                                                               found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET /api/expenses/range - Retrieves expenses within a date range
     *
     * @param startDate Start of the date range (ISO format: 2024-01-01T00:00:00)
     * @param endDate   End of the date range
     * @return List of expenses in the specified date range
     */
    @GetMapping("/range")
    public ResponseEntity<List<ExpenseResponse>> getExpensesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        List<ExpenseResponse> expenses = expenseService.getExpensesByDateRange(startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    /**
     * GET /api/expenses/above - Retrieves expenses above a certain amount
     *
     * @param amount The minimum amount threshold
     * @return List of expenses above the specified amount
     */
    @GetMapping("/above")
    public ResponseEntity<List<ExpenseResponse>> getExpensesAboveAmount(@RequestParam BigDecimal amount) {
        List<ExpenseResponse> expenses = expenseService.getExpensesAboveAmount(amount);
        return ResponseEntity.ok(expenses);
    }

    /**
     * GET /api/expenses/summary - Gets expense summaries grouped by category
     *
     * @return List of summaries showing total amount per category
     */
    @GetMapping("/summary")
    public ResponseEntity<List<ExpenseSummary>> getExpenseSummary() {
        List<ExpenseSummary> summary = expenseService.getTotalByCategory();
        return ResponseEntity.ok(summary);
    }
}

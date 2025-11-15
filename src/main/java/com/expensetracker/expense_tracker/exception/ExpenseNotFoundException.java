package com.expensetracker.expense_tracker.exception;

/**
 * Custom exception thrown when an expense is not found in the database.
 * This provides more specific error handling than generic exceptions.
 */
public class ExpenseNotFoundException extends RuntimeException {

    /**
     * Creates exception with a default message based on expense ID
     *
     * @param id The ID of the expense that was not found
     */
    public ExpenseNotFoundException(Long id) {
        super("Expense not found with id: " + id);
    }

    /**
     * Creates exception with a custom message
     *
     * @param message The custom error message
     */
    public ExpenseNotFoundException(String message) {
        super(message);
    }

    /**
     * Creates exception with a custom message and cause
     *
     * @param message The custom error message
     * @param cause   The underlying cause of the exception
     */
    public ExpenseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

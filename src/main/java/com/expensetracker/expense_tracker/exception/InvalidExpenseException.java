package com.expensetracker.expense_tracker.exception;

/**
 * Custom exception thrown when an expense operation fails due to invalid data
 * or business rules.
 * This separates validation errors from not-found errors for better API
 * responses.
 */
public class InvalidExpenseException extends RuntimeException {

    /**
     * Creates exception with a custom message
     *
     * @param message The custom error message explaining what is invalid
     */
    public InvalidExpenseException(String message) {
        super(message);
    }

    /**
     * Creates exception with a custom message and cause
     *
     * @param message The custom error message
     * @param cause   The underlying cause of the exception
     */
    public InvalidExpenseException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.expensetracker.expense_tracker.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for creating and updating expenses.
 * This class separates the API contract from the database entity,
 * allowing for better validation and encapsulation.
 */
public class ExpenseRequest {

    /**
     * Description of the expense (e.g., "Grocery Shopping", "Fuel")
     * Must not be null or empty
     */
    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    /**
     * Amount spent on this expense
     * Must be positive (greater than zero)
     */
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    /**
     * Category of the expense (e.g., "Food", "Transport", "Entertainment")
     * Must not be null or empty
     */
    @NotBlank(message = "Category is required")
    @Size(max = 255, message = "Category must not exceed 255 characters")
    private String category;

    /**
     * Date and time when the expense occurred
     * Must not be in the future
     */
    @NotNull(message = "Expense date is required")
    @PastOrPresent(message = "Expense date cannot be in the future")
    private LocalDateTime expenseDate;

    // Constructors
    public ExpenseRequest() {
    }

    public ExpenseRequest(String description, BigDecimal amount, String category, LocalDateTime expenseDate) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
    }

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDateTime expenseDate) {
        this.expenseDate = expenseDate;
    }

    @Override
    public String toString() {
        return "ExpenseRequest{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", expenseDate=" + expenseDate +
                '}';
    }
}

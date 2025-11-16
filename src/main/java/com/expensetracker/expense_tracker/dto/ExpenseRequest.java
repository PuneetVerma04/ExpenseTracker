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
     * Transaction date - when the actual expense/transaction occurred
     * Must not be in the future
     */
    @NotNull(message = "Transaction date is required")
    @PastOrPresent(message = "Transaction date cannot be in the future")
    private LocalDateTime transactionDate;

    /**
     * Name of the expense item (e.g., "Grocery Shopping", "Fuel")
     * Must not be null or empty
     */
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    /**
     * Amount spent on this expense
     * Must be positive (greater than zero)
     */
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    @DecimalMin(value = "0.01", message = "Amount must be at least 0.01")
    private BigDecimal amount;

    /**
     * Additional description or notes about the expense (optional)
     */
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    /**
     * Category of the expense (e.g., "Food", "Transport", "Entertainment")
     * Must not be null or empty
     */
    @NotBlank(message = "Category is required")
    @Size(max = 255, message = "Category must not exceed 255 characters")
    private String category;

    /**
     * Tag for personal categorization (e.g., "Personal", "Business", "Family")
     * Optional field from Excel sections
     */
    @Size(max = 100, message = "Tag must not exceed 100 characters")
    private String tag;

    // Constructors
    public ExpenseRequest() {
    }

    public ExpenseRequest(LocalDateTime transactionDate, String name, BigDecimal amount, String category) {
        this.transactionDate = transactionDate;
        this.name = name;
        this.amount = amount;
        this.category = category;
    }

    public ExpenseRequest(LocalDateTime transactionDate, String name, BigDecimal amount,
            String description, String category, String tag) {
        this.transactionDate = transactionDate;
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.tag = tag;
    }

    // Getters and Setters
    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "ExpenseRequest{" +
                "transactionDate=" + transactionDate +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}

package com.expensetracker.expense_tracker.dto;

import com.expensetracker.expense_tracker.entity.Expense;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for expense responses.
 * This class represents the data sent back to clients in API responses,
 * providing a clean separation between internal entity structure and external
 * API contract.
 */
public class ExpenseResponse {

    /**
     * Unique identifier for the expense
     */
    private Long id;

    /**
     * Transaction date - when the actual expense occurred
     */
    private LocalDateTime transactionDate;

    /**
     * Name of the expense item
     */
    private String name;

    /**
     * Amount of money spent
     */
    private BigDecimal amount;

    /**
     * When this expense record was entered in the system
     */
    private LocalDateTime enteredDate;

    /**
     * Additional description or notes (optional)
     */
    private String description;

    /**
     * Category classification of the expense
     */
    private String category;

    /**
     * Tag for personal categorization
     */
    private String tag;

    /**
     * When this expense record was last updated
     */
    private LocalDateTime updatedAt;

    // Constructors
    public ExpenseResponse() {
    }

    public ExpenseResponse(Long id, LocalDateTime transactionDate, String name, BigDecimal amount,
            LocalDateTime enteredDate, String description, String category, String tag, LocalDateTime updatedAt) {
        this.id = id;
        this.transactionDate = transactionDate;
        this.name = name;
        this.amount = amount;
        this.enteredDate = enteredDate;
        this.description = description;
        this.category = category;
        this.tag = tag;
        this.updatedAt = updatedAt;
    }

    /**
     * Factory method to create an ExpenseResponse from an Expense entity.
     * This helps maintain separation between layers and makes entity-to-DTO
     * conversion explicit.
     *
     * @param expense The expense entity to convert
     * @return A new ExpenseResponse populated with data from the entity
     */
    public static ExpenseResponse fromEntity(Expense expense) {
        return new ExpenseResponse(
                expense.getId(),
                expense.getTransactionDate(),
                expense.getName(),
                expense.getAmount(),
                expense.getEnteredDate(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getTag(),
                expense.getUpdatedAt());
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getEnteredDate() {
        return enteredDate;
    }

    public void setEnteredDate(LocalDateTime enteredDate) {
        this.enteredDate = enteredDate;
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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "ExpenseResponse{" +
                "id=" + id +
                ", transactionDate=" + transactionDate +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", enteredDate=" + enteredDate +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", tag='" + tag + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

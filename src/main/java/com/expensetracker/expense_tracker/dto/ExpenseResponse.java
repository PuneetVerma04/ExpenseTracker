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
     * Description of what the expense was for
     */
    private String description;

    /**
     * Amount of money spent
     */
    private BigDecimal amount;

    /**
     * Category classification of the expense
     */
    private String category;

    /**
     * When the expense occurred
     */
    private LocalDateTime expenseDate;

    /**
     * When this expense record was created in the system
     */
    private LocalDateTime createdAt;

    /**
     * When this expense record was last updated
     */
    private LocalDateTime updatedAt;

    // Constructors
    public ExpenseResponse() {
    }

    public ExpenseResponse(Long id, String description, BigDecimal amount, String category,
            LocalDateTime expenseDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
        this.createdAt = createdAt;
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
                expense.getDescription(),
                expense.getAmount(),
                expense.getCategory(),
                expense.getExpenseDate(),
                expense.getCreatedAt(),
                expense.getUpdatedAt());
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", expenseDate=" + expenseDate +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

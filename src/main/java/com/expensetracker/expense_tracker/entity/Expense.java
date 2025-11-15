package com.expensetracker.expense_tracker.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * JPA Entity representing an expense record in the database.
 * This class maps to the "expenses" table and contains all expense information
 * including description, amount, category, and timestamps.
 */
@Entity
@Table(name = "expenses")
public class Expense {

    /**
     * Primary key - auto-generated ID for each expense
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Description of what the expense was for (e.g., "Grocery Shopping", "Fuel")
     * Required field - cannot be null in database
     */
    @Column(nullable = false)
    private String description;

    /**
     * Amount spent on this expense
     * Required field - stored as DECIMAL/NUMERIC in database for precision
     */
    @Column(nullable = false)
    private BigDecimal amount;

    /**
     * Category classification for the expense (e.g., "Food", "Transport",
     * "Entertainment")
     * Required field - used for grouping and filtering expenses
     */
    @Column(nullable = false)
    private String category;

    /**
     * Date and time when the expense occurred
     * Required field - can be different from when the record was created
     */
    @Column(name = "expense_date", nullable = false)
    private LocalDateTime expenseDate;

    /**
     * Timestamp when this record was first created in the database
     * Automatically set by @PrePersist lifecycle callback
     */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /**
     * Timestamp when this record was last updated
     * Automatically updated by @PreUpdate lifecycle callback
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Constructors

    /**
     * Default constructor required by JPA
     */
    public Expense() {
    }

    /**
     * Constructor for creating a new expense with all required fields
     *
     * @param description What the expense was for
     * @param amount      How much was spent
     * @param category    Category classification
     * @param expenseDate When the expense occurred
     */
    public Expense(String description, BigDecimal amount, String category, LocalDateTime expenseDate) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
    }

    // Lifecycle callbacks

    /**
     * JPA lifecycle callback - automatically called before persisting a new expense
     * Sets both createdAt and updatedAt to current timestamp
     */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    /**
     * JPA lifecycle callback - automatically called before updating an existing
     * expense
     * Updates the updatedAt timestamp to current time
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
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
        return "Expense{" +
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

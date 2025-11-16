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
     * Primary key - auto-generated ID for each expense (SerialNo)
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Transaction date - when the actual expense/transaction occurred
     * This is the date from your Excel file
     */
    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    /**
     * Name of the expense item (e.g., "Grocery Shopping", "Fuel")
     * This is the "Item" column from your Excel
     */
    @Column(nullable = false)
    private String name;

    /**
     * Amount spent on this expense
     * Required field - stored as DECIMAL/NUMERIC in database for precision
     */
    @Column(nullable = false)
    private BigDecimal amount;

    /**
     * Entered date - timestamp when this record was first created in the database
     * Automatically set by @PrePersist lifecycle callback
     */
    @Column(name = "entered_date")
    private LocalDateTime enteredDate;

    /**
     * Additional description or notes about the expense (optional)
     * Can provide more context beyond just the name
     */
    @Column(length = 500)
    private String description;

    /**
     * Category classification for the expense (e.g., "Food", "Transport",
     * "Entertainment")
     * Used for grouping and filtering expenses
     */
    @Column(nullable = false)
    private String category;

    /**
     * Tag field - for your personal categorization from Excel
     * This represents the separate category areas in your Excel file
     * Examples: "Personal", "Business", "Family", etc.
     */
    @Column
    private String tag;

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
     * @param name            Name/Item of the expense
     * @param amount          How much was spent
     * @param category        Category classification
     * @param transactionDate When the expense occurred
     */
    public Expense(String name, BigDecimal amount, String category, LocalDateTime transactionDate) {
        this.name = name;
        this.amount = amount;
        this.category = category;
        this.transactionDate = transactionDate;
    }

    // Lifecycle callbacks

    /**
     * JPA lifecycle callback - automatically called before persisting a new expense
     * Sets enteredDate and updatedAt to current timestamp
     */
    @PrePersist
    protected void onCreate() {
        enteredDate = LocalDateTime.now();
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
        return "Expense{" +
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

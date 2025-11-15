package com.expensetracker.expense_tracker.dto;

import java.math.BigDecimal;

/**
 * Data Transfer Object for expense summary and aggregated data.
 * Used for reporting and analytics, showing totals by category or time period.
 */
public class ExpenseSummary {

    /**
     * The category name (e.g., "Food", "Transport")
     */
    private String category;

    /**
     * Total amount spent in this category
     */
    private BigDecimal totalAmount;

    /**
     * Number of expenses in this category
     */
    private Long count;

    // Constructors
    public ExpenseSummary() {
    }

    public ExpenseSummary(String category, BigDecimal totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }

    public ExpenseSummary(String category, BigDecimal totalAmount, Long count) {
        this.category = category;
        this.totalAmount = totalAmount;
        this.count = count;
    }

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ExpenseSummary{" +
                "category='" + category + '\'' +
                ", totalAmount=" + totalAmount +
                ", count=" + count +
                '}';
    }
}

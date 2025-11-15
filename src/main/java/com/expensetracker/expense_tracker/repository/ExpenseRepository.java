package com.expensetracker.expense_tracker.repository;

import com.expensetracker.expense_tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Expense entity data access.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 * Spring Data JPA automatically implements this interface at runtime.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * Finds all expenses in a specific category
     * Uses Spring Data JPA method naming convention to auto-generate query
     *
     * @param category The category to filter by
     * @return List of expenses in the specified category
     */
    List<Expense> findByCategory(String category);

    /**
     * Finds expenses that occurred within a date range (inclusive)
     * Uses Spring Data JPA method naming convention with Between keyword
     *
     * @param startDate Start of the date range
     * @param endDate   End of the date range
     * @return List of expenses between the two dates
     */
    List<Expense> findByExpenseDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    /**
     * Finds expenses with amount greater than the specified threshold
     * Useful for filtering high-value expenses
     *
     * @param amount The minimum amount (exclusive)
     * @return List of expenses with amount greater than the specified value
     */
    List<Expense> findByAmountGreaterThan(BigDecimal amount);

    /**
     * Aggregates expenses by category, calculating total amount per category
     * Uses custom JPQL query with GROUP BY and SUM
     *
     * @return List of Object arrays where [0] is category (String) and [1] is total
     *         (BigDecimal)
     */
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e GROUP BY e.category")
    List<Object[]> getTotalByCategory();

    /**
     * Searches for expenses by description with case-insensitive partial matching
     * Uses Spring Data JPA method naming with ContainingIgnoreCase keywords
     *
     * @param keyword The search keyword to look for in descriptions
     * @return List of expenses whose description contains the keyword
     *         (case-insensitive)
     */
    List<Expense> findByDescriptionContainingIgnoreCase(String keyword);
}

package com.expensetracker.expense_tracker.controller;

import com.expensetracker.expense_tracker.dto.ExpenseRequest;
import com.expensetracker.expense_tracker.dto.ExpenseResponse;
import com.expensetracker.expense_tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Web Controller for serving Thymeleaf templates.
 * Handles browser requests and returns HTML pages instead of JSON.
 * All endpoints return view names that Thymeleaf resolves to HTML templates.
 */
@Controller
@RequestMapping("/expenses")
public class WebController {

    private final ExpenseService expenseService;

    /**
     * Constructor injection for ExpenseService
     *
     * @param expenseService The service handling business logic
     */
    public WebController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Home page redirect - redirects root URL to expense list
     *
     * @return Redirect to /expenses/list
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/expenses/list";
    }

    /**
     * Displays all expenses in a table view
     * Supports filtering by category and searching by description
     *
     * @param category Optional category filter parameter
     * @param search   Optional search keyword parameter
     * @param model    Spring MVC model to pass data to the view
     * @return View name "list" (resolves to templates/list.html)
     */
    @GetMapping("/list")
    public String listExpenses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String search,
            Model model) {

        List<ExpenseResponse> expenses;

        // Apply filters based on parameters
        if (search != null && !search.isEmpty()) {
            expenses = expenseService.searchExpenses(search);
        } else if (category != null && !category.isEmpty()) {
            expenses = expenseService.getExpensesByCategory(category);
        } else {
            expenses = expenseService.getAllExpenses();
        }

        // Add data to model for Thymeleaf template
        model.addAttribute("expenses", expenses);
        model.addAttribute("currentCategory", category);
        model.addAttribute("currentSearch", search);

        return "list"; // Returns templates/list.html
    }

    /**
     * Displays the form for adding a new expense
     * Creates an empty ExpenseRequest object for form binding
     *
     * @param model Spring MVC model to pass data to the view
     * @return View name "form" (resolves to templates/form.html)
     */
    @GetMapping("/new")
    public String showNewExpenseForm(Model model) {
        model.addAttribute("expense", new ExpenseRequest());
        model.addAttribute("isEdit", false);
        return "form"; // Returns templates/form.html
    }

    /**
     * Processes the form submission for creating a new expense
     * Validates the form data and redirects to list on success
     *
     * @param expenseRequest     The form data bound to ExpenseRequest object
     * @param bindingResult      Spring validation result object
     * @param redirectAttributes Flash attributes for success/error messages
     * @return Redirect to list page on success, or back to form on validation error
     */
    @PostMapping("/new")
    public String createExpense(
            @Valid @ModelAttribute("expense") ExpenseRequest expenseRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        // If validation fails, return to form with error messages
        if (bindingResult.hasErrors()) {
            return "form";
        }

        try {
            expenseService.createExpense(expenseRequest);
            redirectAttributes.addFlashAttribute("successMessage", "Expense created successfully!");
            return "redirect:/expenses/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error creating expense: " + e.getMessage());
            return "redirect:/expenses/new";
        }
    }

    /**
     * Displays the form for editing an existing expense
     * Loads the expense data by ID and populates the form
     *
     * @param id    The ID of the expense to edit
     * @param model Spring MVC model to pass data to the view
     * @return View name "form" with expense data populated
     */
    @GetMapping("/edit/{id}")
    public String showEditExpenseForm(@PathVariable Long id, Model model) {
        try {
            ExpenseResponse expense = expenseService.getExpenseById(id);

            // Convert ExpenseResponse to ExpenseRequest for form binding
            ExpenseRequest expenseRequest = new ExpenseRequest();
            expenseRequest.setTransactionDate(expense.getTransactionDate());
            expenseRequest.setName(expense.getName());
            expenseRequest.setAmount(expense.getAmount());
            expenseRequest.setDescription(expense.getDescription());
            expenseRequest.setCategory(expense.getCategory());
            expenseRequest.setTag(expense.getTag());

            model.addAttribute("expense", expenseRequest);
            model.addAttribute("expenseId", id);
            model.addAttribute("isEdit", true);
            return "form";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Expense not found: " + e.getMessage());
            return "redirect:/expenses/list";
        }
    }

    /**
     * Processes the form submission for updating an existing expense
     * Validates the form data and redirects to list on success
     *
     * @param id                 The ID of the expense to update
     * @param expenseRequest     The updated form data
     * @param bindingResult      Spring validation result object
     * @param redirectAttributes Flash attributes for success/error messages
     * @return Redirect to list page on success
     */
    @PostMapping("/edit/{id}")
    public String updateExpense(
            @PathVariable Long id,
            @Valid @ModelAttribute("expense") ExpenseRequest expenseRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {

        // If validation fails, return to form with error messages
        if (bindingResult.hasErrors()) {
            model.addAttribute("expenseId", id);
            model.addAttribute("isEdit", true);
            return "form";
        }

        try {
            expenseService.updateExpense(id, expenseRequest);
            redirectAttributes.addFlashAttribute("successMessage", "Expense updated successfully!");
            return "redirect:/expenses/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error updating expense: " + e.getMessage());
            return "redirect:/expenses/edit/" + id;
        }
    }

    /**
     * Deletes an expense by ID
     * No confirmation page - directly deletes and redirects
     *
     * @param id                 The ID of the expense to delete
     * @param redirectAttributes Flash attributes for success/error messages
     * @return Redirect to list page
     */
    @GetMapping("/delete/{id}")
    public String deleteExpense(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            expenseService.deleteExpense(id);
            redirectAttributes.addFlashAttribute("successMessage", "Expense deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting expense: " + e.getMessage());
        }
        return "redirect:/expenses/list";
    }
}

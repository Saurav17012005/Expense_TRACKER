package model;

public class Expense {
    private int id;
    private int userId;
    private double amount;
    private String category;
    private String description;

    // Constructor for database retrieval
    public Expense(int id, int userId, double amount, String category, String description) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    // Constructor for adding a new expense
    public Expense(int userId, double amount, String category, String description) {
        this.userId = userId;
        this.amount = amount;
        this.category = category;
        this.description = description;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
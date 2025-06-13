package dao;

import model.Expense;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    /** ✅ Adds a new expense to the database **/
    public boolean addExpense(Expense expense) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO Expenses (user_id, amount, category, description) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, expense.getUserId());
            stmt.setDouble(2, expense.getAmount());
            stmt.setString(3, expense.getCategory());
            stmt.setString(4, expense.getDescription());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Expense added successfully! Amount: $" + expense.getAmount());
                return true;
            } else {
                System.err.println("❌ Expense NOT added: No rows affected.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Database error while adding expense: " + e.getMessage());
            return false;
        }
    }

    /** ✅ Fetches all expenses separately **/
    public List<Expense> getAllExpenses(int userId) {
        List<Expense> expenses = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT amount, category, description FROM Expenses WHERE user_id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                expenses.add(new Expense(userId, rs.getDouble("amount"), rs.getString("category"), rs.getString("description")));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error retrieving expenses: " + e.getMessage());
        }
        return expenses;
    }

    /** ✅ Fetches total expenses for a given user ID **/
    public double getTotalExpenses(int userId) {
        double total = 0;
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT SUM(amount) AS total FROM Expenses WHERE user_id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble("total");
                System.out.println("✅ Total expenses fetched: $" + total);
            } else {
                System.err.println("❌ No expenses found for user ID: " + userId);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error calculating total expenses: " + e.getMessage());
        }
        return total;
    }
}
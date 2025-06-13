package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /** ✅ Registers a new user only if the username doesn't exist **/
    public boolean registerUser(String username, String password) {
        if (isUserExists(username)) {
            System.err.println("❌ Registration failed: Username already exists.");
            return false; // ✅ Prevent duplicate registrations
        }

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO Users (username, passwordhash) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password); // ✅ Consider hashing passwords for security

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ User registered successfully! Username: " + username);
                return true;
            } else {
                System.err.println("❌ Registration failed: No rows affected.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("❌ Database error while registering user: " + e.getMessage());
            return false;
        }
    }

    /** ✅ Checks if a username already exists before registering **/
    public boolean isUserExists(String username) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT COUNT(*) FROM Users WHERE username=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            return rs.next() && rs.getInt(1) > 0; // ✅ If count > 0, user exists
        } catch (SQLException e) {
            System.err.println("❌ Error checking user existence: " + e.getMessage());
            return false;
        }
    }

    /** ✅ Authenticates a user and returns their user ID **/
    public int authenticateUser(String username, String password) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT id FROM Users WHERE username=? AND passwordhash=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next() ? rs.getInt("id") : -1; // ✅ Return user ID if authentication succeeds
        } catch (SQLException e) {
            System.err.println("❌ Error logging in: " + e.getMessage());
            return -1;
        }
    }
}
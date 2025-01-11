package com.amdocs.cet.dao;

import com.amdocs.cet.bean.User;
import java.sql.*;

public class UserDao {

    private static final String URL = "jdbc:mysql://localhost:3306/cet_db1";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    
    // Method to get the database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Register a new user (stores hashed password)
    public boolean registerUser(User user) {
        try (Connection connection = getConnection()) {
            String query = "INSERT INTO Users (username, password, fullname) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, user.getUsername());
                stmt.setString(2, user.getPassword()); // Storing the hashed password
                stmt.setString(3, user.getFullname());
                int rowsAffected = stmt.executeUpdate();
                return rowsAffected > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validate user login, return user_id if valid, else return -1
    public int validateUser(String username, String hashedPassword) {
        try (Connection connection = getConnection()) {
            String query = "SELECT user_id FROM Users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, hashedPassword);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    // If a user is found, return the user_id
                    return rs.getInt("user_id");
                } else {
                    // If no user found, return -1
                    return -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1; // In case of an error, return -1
        }
    }
}

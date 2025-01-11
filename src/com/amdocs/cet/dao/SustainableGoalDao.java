package com.amdocs.cet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.amdocs.cet.bean.UserActivity;

public class SustainableGoalDao {
    
    // Get the last user activity date (corrected the table name and column names)
    public static LocalDate getLastUserActivityDate(int userId) {
        String query = "SELECT MAX(activity_date) FROM UserActivity WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDate(1).toLocalDate();  // Return the last activity date as LocalDate
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no activity found
    }
    
    // Get user activities for a date range (fixed table and column names)
    public static List<UserActivity> getUserActivitiesForDateRange(int userId, LocalDate startDate, LocalDate endDate) {
        List<UserActivity> userActivities = new ArrayList<>();
        
        // SQL query updated to handle both start and end date for the activity range
        String query = "SELECT ua.user_activity_id, ua.user_id, ua.activity_category_id, ua.input, ua.activity_date, ua.total_emission, " +
                       "a.activity_category, ac.activity_subcategory, ac.activity_subsubcategory " +
                       "FROM UserActivity ua " +
                       "JOIN ActivityCategories ac ON ua.activity_category_id = ac.activity_category_id " +
                       "JOIN Activities a ON ac.activity_id = a.activity_id " +
                       "WHERE ua.user_id = ? AND ua.activity_date BETWEEN ? AND ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            // Set userId, startDate, and endDate for the query
            ps.setInt(1, userId);
            ps.setDate(2, java.sql.Date.valueOf(startDate));  // Start date
            ps.setDate(3, java.sql.Date.valueOf(endDate));    // End date

            try (ResultSet rs = ps.executeQuery()) {
                // Fetch all activities within the date range
                while (rs.next()) {
                    UserActivity userActivity = new UserActivity(
                        rs.getInt("user_activity_id"),
                        rs.getInt("user_id"),
                        rs.getInt("activity_category_id"),
                        rs.getBigDecimal("input"),
                        rs.getDate("activity_date").toLocalDate(),
                        rs.getBigDecimal("total_emission"),
                        rs.getString("activity_category"),
                        rs.getString("activity_subcategory"),
                        rs.getString("activity_subsubcategory")
                    );
                    userActivities.add(userActivity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userActivities;
    }
}

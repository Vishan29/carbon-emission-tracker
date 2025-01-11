package com.amdocs.cet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.amdocs.cet.bean.UserActivity;

public class ActivityBasedEmissionDataDao {

    public static List<UserActivity> getUserActivitiesByCategory(int userId, String category) {
        List<UserActivity> userActivities = new ArrayList<>();
        String query = "SELECT ua.user_activity_id, ua.user_id, ua.activity_category_id, ua.input, ua.activity_date, ua.total_emission, " +
                       "a.activity_category, ac.activity_subcategory, ac.activity_subsubcategory " +
                       "FROM UserActivity ua " +
                       "JOIN ActivityCategories ac ON ua.activity_category_id = ac.activity_category_id " +
                       "JOIN Activities a ON ac.activity_id = a.activity_id " +
                       "WHERE ua.user_id = ? AND a.activity_category = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setString(2, category);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    UserActivity userActivity = new UserActivity(
                        rs.getInt("user_activity_id"),
                        rs.getInt("user_id"),
                        rs.getInt("activity_category_id"), // activity_category_id (subcategoryId)
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

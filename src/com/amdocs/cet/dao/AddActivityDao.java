package com.amdocs.cet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class AddActivityDao {

//	Retrieve categories from the Activities table
    public static List<String> getActivityCategories() {
        List<String> categories = new ArrayList<>();
        String query = "SELECT activity_category FROM Activities";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query); 
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                categories.add(rs.getString("activity_category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categories;
    }

    public static List<String> getActivitySubcategories(int activityId) {
        List<String> subcategories = new ArrayList<>();
        String query = "SELECT DISTINCT activity_subcategory FROM ActivityCategories WHERE activity_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, activityId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subcategories.add(rs.getString("activity_subcategory"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subcategories;
    }

    public static List<String> getActivitySubsubcategories(int activityId, String selectedSubcategory) {
        List<String> subsubcategories = new ArrayList<>();
        String query = "SELECT activity_subsubcategory FROM ActivityCategories WHERE activity_id = ? AND activity_subcategory = ?";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, activityId);
            ps.setString(2, selectedSubcategory);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    subsubcategories.add(rs.getString("activity_subsubcategory"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return subsubcategories;
    }

    public static String getInputUnit(String category) {
        String query = "SELECT input_unit FROM Activities WHERE activity_category = ?";
        String unit = "";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, category);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    unit = rs.getString("input_unit");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return unit;
    }

    public static double getEmissionRate(int activityId, int subcategoryId) {
        String query = "SELECT emission_per_unit FROM ActivityCategories WHERE activity_id = ? AND activity_category_id = ?";
        double emissionRate = 0.0;
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, activityId);
            ps.setInt(2, subcategoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emissionRate = rs.getDouble("emission_per_unit");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return emissionRate;
    }

    public static void insertUserActivity(int userId, int subcategoryId, double input, Date activityDate) {
        String query = "INSERT INTO UserActivity (user_id, activity_category_id, input, activity_date) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, userId);
            ps.setInt(2, subcategoryId);
            ps.setDouble(3, input);
            ps.setDate(4, activityDate);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public static int getActivityCategoryId(String selectedSubcategory, String selectedSubsubcategory) {
		// TODO Auto-generated method stub
		String query = "SELECT activity_category_id FROM ActivityCategories where activity_subcategory = ? and activity_subsubcategory = ?";
        int activity_category_id = 1;
        
        try (Connection conn = DatabaseConnection.getConnection(); 
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, selectedSubcategory);
            ps.setString(2, selectedSubsubcategory);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                	activity_category_id = rs.getInt("activity_category_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return activity_category_id;
	}
}

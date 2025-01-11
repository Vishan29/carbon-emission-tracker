package com.amdocs.cet.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActivityDao {
	public List<String> getAllCategories() {
	    String sql = "SELECT DISTINCT activity_category FROM Activities";
	    List<String> categories = new ArrayList<>();
	    try (Connection conn = UserDao.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        
	        while (rs.next()) {
	            categories.add(rs.getString("activity_category"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return categories;
	}
	
	public List<String> getSubcategoriesByCategory(String category) {
	    String sql = "SELECT activity_subcategory FROM ActivityCategories WHERE activity_category = ?";
	    List<String> subcategories = new ArrayList<String>();
	    try (Connection conn = UserDao.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, category);
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

	public List<String> getSubsubcategoriesBySubcategory(String subcategory) {
	    String sql = "SELECT activity_subsubcategory FROM ActivityCategories WHERE activity_subcategory = ?";
	    List<String> subsubcategories = new ArrayList<>();
	    try (Connection conn = UserDao.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, subcategory);
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
	
	public double getEmissionPerUnit(String category, String subcategory, String subsubcategory) {
	    String sql = "SELECT emission_per_unit FROM ActivityCategories WHERE activity_category = ? AND activity_subcategory = ? AND (activity_subsubcategory = ? OR activity_subsubcategory IS NULL)";
	    double emissionPerUnit = 0.0;
	    try (Connection conn = UserDao.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, category);
	        ps.setString(2, subcategory);
	        ps.setString(3, subsubcategory);
	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                emissionPerUnit = rs.getDouble("emission_per_unit");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return emissionPerUnit;
	}

	public String getUnitForSubcategory(String subcategory) {
	    String sql = "SELECT input_unit FROM Activities WHERE activity_category = (SELECT activity_category FROM ActivityCategories WHERE activity_subcategory = ? LIMIT 1)";
	    String unit = "";
	    try (Connection conn = UserDao.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        
	        ps.setString(1, subcategory);
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

}

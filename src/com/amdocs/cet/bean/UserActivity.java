package com.amdocs.cet.bean;

import java.math.BigDecimal;
import java.sql.Date;	
import java.time.LocalDate;

public class UserActivity {
    private int userActivityId;
    private int userId;
    private int activityId;
    private BigDecimal input;
    private LocalDate activityDate;
    private BigDecimal totalEmission;

    // New fields for activity category, subcategory, and subsubcategory
    private String activityCategory;
    private String activitySubcategory;
    private String activitySubsubcategory;

//     Constructor including the new fields
    public UserActivity(int userActivityId, int userId, int activityId, BigDecimal input, LocalDate activityDate, 
                        BigDecimal totalEmission, String activityCategory, String activitySubcategory, String activitySubsubcategory) {
        this.userActivityId = userActivityId;
        this.userId = userId;
        this.activityId = activityId;
        this.input = input;
        this.activityDate = activityDate;
        this.totalEmission = totalEmission;
        this.activityCategory = activityCategory;
        this.activitySubcategory = activitySubcategory;
        this.activitySubsubcategory = activitySubsubcategory;
    }

    // Default constructor
    public UserActivity() {
        // Default constructor
    }

	// Getters and Setters for all fields
    public int getUserActivityId() {
        return userActivityId;
    }

    public void setUserActivityId(int userActivityId) {
        this.userActivityId = userActivityId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public BigDecimal getInput() {
        return input;
    }

    public void setInput(BigDecimal input) {
        this.input = input;
    }

    public LocalDate getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDate activityDate) {
        this.activityDate = activityDate;
    }

    public BigDecimal getTotalEmission() {
        return totalEmission;
    }

    public void setTotalEmission(BigDecimal totalEmission) {
        this.totalEmission = totalEmission;
    }

    // Getters and Setters for the new fields
    public String getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }

    public String getActivitySubcategory() {
        return activitySubcategory;
    }

    public void setActivitySubcategory(String activitySubcategory) {
        this.activitySubcategory = activitySubcategory;
    }

    public String getActivitySubsubcategory() {
        return activitySubsubcategory;
    }

    public void setActivitySubsubcategory(String activitySubsubcategory) {
        this.activitySubsubcategory = activitySubsubcategory;
    }
}

package com.amdocs.cet.bean;

public class Activity {
    private int activityId;
    private String activityCategory;
    private String inputUnit;
    
    // Constructor
    public Activity(int activityId, String activityCategory, String inputUnit) {
        this.activityId = activityId;
        this.activityCategory = activityCategory;
        this.inputUnit = inputUnit;
    }

    // Getters and Setters
    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public String getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {
        this.activityCategory = activityCategory;
    }

    public String getInputUnit() {
        return inputUnit;
    }

    public void setInputUnit(String inputUnit) {
        this.inputUnit = inputUnit;
    }
}

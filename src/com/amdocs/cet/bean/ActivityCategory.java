package com.amdocs.cet.bean;

import java.math.BigDecimal;

public class ActivityCategory {
    private int activityCategoryId;
    private int activityId;
    private String activitySubcategory;
    private String activitySubsubcategory;
    private BigDecimal emissionPerUnit;

    // Constructor
    public ActivityCategory(int activityCategoryId, int activityId, String activitySubcategory, 
                            String activitySubsubcategory, BigDecimal emissionPerUnit) {
        this.activityCategoryId = activityCategoryId;
        this.activityId = activityId;
        this.activitySubcategory = activitySubcategory;
        this.activitySubsubcategory = activitySubsubcategory;
        this.emissionPerUnit = emissionPerUnit;
    }

    public ActivityCategory() {
		// TODO Auto-generated constructor stub
	}

	// Getters and Setters
    public int getActivityCategoryId() {
        return activityCategoryId;
    }

    public void setActivityCategoryId(int activityCategoryId) {
        this.activityCategoryId = activityCategoryId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
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

    public BigDecimal getEmissionPerUnit() {
        return emissionPerUnit;
    }

    public void setEmissionPerUnit(BigDecimal emissionPerUnit) {
        this.emissionPerUnit = emissionPerUnit;
    }
}

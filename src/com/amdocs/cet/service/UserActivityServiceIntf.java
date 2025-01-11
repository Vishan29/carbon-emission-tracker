package com.amdocs.cet.service;

public interface UserActivityServiceIntf {                 
	void addActivityData(int userId);	// 1. Add activity data
    void trackEmissionFootprint(int userId);            // 2. Track emission footprint data
    void getActivityBasedFootprintData(int userId);     // 3. Activity based footprint data
    void calculateActivityBasedEmission(int userId);    // 4. Activity based emission calculator
    void setSustainabilityGoal(int userId);             // 5. Sustainability goal setting
}

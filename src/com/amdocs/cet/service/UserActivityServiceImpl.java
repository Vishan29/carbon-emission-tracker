package com.amdocs.cet.service;

import com.amdocs.cet.bean.UserActivity;
import com.amdocs.cet.dao.ActivityBasedEmissionDataDao;
import com.amdocs.cet.dao.AddActivityDao;        // Assuming this exists to interact with the DB
import com.amdocs.cet.dao.SustainableGoalDao;
import com.amdocs.cet.dao.TrackActivityDao;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserActivityServiceImpl implements UserActivityServiceIntf {

    public UserActivityServiceImpl() {
		// TODO Auto-generated constructor stub
	}
    
    //Function to make user choice
    private int getUserChoice(int maxChoice) {
        @SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
        int choice = -1;  // Initialize with an invalid choice to start the loop
        while (true) {
            try {
                System.out.print("Enter your choice (1-" + maxChoice + "): ");
                choice = scanner.nextInt();
                if (choice >= 1 && choice <= maxChoice) {
                    break;  // Valid choice, exit the loop
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                // Catch the exception when the input is not an integer
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next();  // Consume the invalid input to prevent an infinite loop
            }
        }
        return choice;
    }

    @Override
    public void addActivityData(int userId) {
        // Step 1: Retrieve categories from the Activities table
        List<String> activityCategories = AddActivityDao.getActivityCategories();
        
        // Display the categories and let the user choose one
        System.out.println("Select an Activity Category:");
        for (int i = 0; i < activityCategories.size(); i++) {
            System.out.println((i + 1) + ". " + activityCategories.get(i));
        }
        int activityChoice = getUserChoice(activityCategories.size()) - 1;
        String selectedCategory = activityCategories.get(activityChoice);
        
        // Step 2: We're retrieving subcategories based on the selected category
        List<String> subcategories = AddActivityDao.getActivitySubcategories(activityChoice + 1);
        
        // Display the subcategories and let the user choose one
        System.out.println("Select a Subcategory for " + selectedCategory + ":");
        for (int i = 0; i < subcategories.size(); i++) {
            System.out.println((i + 1) + ". " + subcategories.get(i));
        }
        int subcategoryChoice = getUserChoice(subcategories.size()) - 1;
        String selectedSubcategory = subcategories.get(subcategoryChoice);
        
        // Step 3: Retrieve subsubcategories (if any) based on the selected subcategory
        List<String> subsubcategories = AddActivityDao.getActivitySubsubcategories(activityChoice + 1, selectedSubcategory);
        
        int activity_category_id;
        // Display the subsubcategories if any
        if (!subsubcategories.isEmpty()) {
            System.out.println("Select a Subsubcategory:");
            for (int i = 0; i < subsubcategories.size(); i++) {
                System.out.println((i + 1) + ". " + subsubcategories.get(i));
            }
            int subsubCategoryChoice = getUserChoice(subsubcategories.size()) - 1;
            String selectedSubsubCategory = subsubcategories.get(subsubCategoryChoice);
            activity_category_id = AddActivityDao.getActivityCategoryId(selectedSubcategory, selectedSubsubCategory);
            // Use selectedSubsubCategory later in the emission calculation (if needed)
        } else {
            activity_category_id = AddActivityDao.getActivityCategoryId(selectedSubcategory, null);
        }
        
        // Step 4: Collect input value from the user based on the category unit.
        System.out.print("Enter the value for " + AddActivityDao.getInputUnit(selectedCategory) + ": ");
        @SuppressWarnings("resource")
		double input = new Scanner(System.in).nextDouble();
        
        // Step 5: Collect the date of the activity
        System.out.print("Enter the date of the activity (yyyy-mm-dd): ");
        @SuppressWarnings("resource")
		String dateInput = new Scanner(System.in).nextLine();
        Date activityDate = Date.valueOf(dateInput);  // Convert String to Date
        
        // Step 6: Retrieve emission rate for the selected subcategory
        //double emissionRate = AddActivityDao.getEmissionRate(activityChoice + 1, activity_category_id);
        //double totalEmission = input * emissionRate;
        
        // Step 7: Insert the record into the UserActivity table
        AddActivityDao.insertUserActivity(userId, activity_category_id, input, activityDate);
        
        System.out.println("Activity recorded successfully");
    }
   

    @Override
    public void trackEmissionFootprint(int userId) {
		// Fetch user activities from DB using DAO.
		List<UserActivity> userActivities = TrackActivityDao.getUserActivities(userId);

		if (userActivities.isEmpty()) {
			System.out.println("No activities found for the user.");
		} else {
			// Display the activity details and their emissions
			BigDecimal totalFootprint = BigDecimal.ZERO;
			System.out.println("User " + userId + "'s Emission Footprint:");

			// Print the header of the table
			System.out.printf("%-20s%-30s%-20s%-20s%-15s%-20s\n", "Activity Category", "Activity Subcategory",
					"Activity Subsubcategory", "Date", "Distance", "Total Emission");
			System.out.println(
					"--------------------------------------------------------------------------------------------------------------------");

			// Loop through each user activity and display it in table format
			for (UserActivity activity : userActivities) {
				System.out.printf("%-20s%-30s%-20s%-20s%-15.2f%-20.2f\n", activity.getActivityCategory(),
						activity.getActivitySubcategory(), activity.getActivitySubsubcategory(),
						activity.getActivityDate(), activity.getInput(), activity.getTotalEmission());
				totalFootprint = totalFootprint.add(activity.getTotalEmission());
			}

			// Print the total emission footprint
			System.out.println(
					"---------------------------------------------------------------------------------------------------");
			System.out.printf("%-20s%-30s%-20s%-20s%-15s%-20.2f\n", "TOTAL EMISSION FOOTPRINT", "", "", "", "",
					totalFootprint);
		}
	}

    @Override
    public void getActivityBasedFootprintData(int userId) {
        // Step 1: Retrieve categories from the Activities table
        List<String> activityCategories = AddActivityDao.getActivityCategories();
        
        if (activityCategories.isEmpty()) {
            System.out.println("No activity categories found.");
            return;
        }

        // Display the categories and let the user choose one
        System.out.println("Select an Activity Category to view footprint data:");
        for (int i = 0; i < activityCategories.size(); i++) {
            System.out.println((i + 1) + ". " + activityCategories.get(i));
        }

        int activityChoice = getUserChoice(activityCategories.size()) - 1;
        String selectedCategory = activityCategories.get(activityChoice);

        // Step 2: Retrieve user activities based on the selected category
        List<UserActivity> userActivities = ActivityBasedEmissionDataDao.getUserActivitiesByCategory(userId, selectedCategory);

        if (userActivities.isEmpty()) {
            System.out.println("No activities found for the selected category.");
        } else {
            // Step 3: Display the activity details and their emissions in table format
            BigDecimal totalFootprint = BigDecimal.ZERO;
            System.out.println("User " + userId + "'s Emission Footprint for " + selectedCategory + ":");

            // Print the header of the table
            System.out.printf("%-20s%-30s%-20s%-20s%-15s%-20s\n", "Activity Category", "Activity Subcategory",
                    "Activity Subsubcategory", "Date", "Distance", "Total Emission");
            System.out.println(
                    "--------------------------------------------------------------------------------------------------------------------");

            // Loop through each user activity and display it in table format
            for (UserActivity activity : userActivities) {
                System.out.printf("%-20s%-30s%-20s%-20s%-15.2f%-20.2f\n", activity.getActivityCategory(),
                        activity.getActivitySubcategory(), activity.getActivitySubsubcategory(),
                        activity.getActivityDate(), activity.getInput(), activity.getTotalEmission());
                totalFootprint = totalFootprint.add(activity.getTotalEmission());
            }

            // Print the total emission footprint
            System.out.println(
                    "---------------------------------------------------------------------------------------------------");
            System.out.printf("%-20s%-30s%-20s%-20s%-15s%-20.2f\n", "TOTAL EMISSION FOOTPRINT", "", "", "", "",
                    totalFootprint);
        }
    }


    @Override
    public void calculateActivityBasedEmission(int userId) {
    	// Step 1: Retrieve categories from the Activities table
        List<String> activityCategories = AddActivityDao.getActivityCategories();
        
        // Display the categories and let the user choose one
        System.out.println("Select an Activity Category:");
        for (int i = 0; i < activityCategories.size(); i++) {
            System.out.println((i + 1) + ". " + activityCategories.get(i));
        }
        int activityChoice = getUserChoice(activityCategories.size()) - 1;
        String selectedCategory = activityCategories.get(activityChoice);
        
        // Step 2: Retrieve subcategories based on the selected category
        List<String> subcategories = AddActivityDao.getActivitySubcategories(activityChoice + 1);
        
        // Display the subcategories and let the user choose one
        System.out.println("Select a Subcategory for " + selectedCategory + ":");
        for (int i = 0; i < subcategories.size(); i++) {
            System.out.println((i + 1) + ". " + subcategories.get(i));
        }
        int subcategoryChoice = getUserChoice(subcategories.size()) - 1;
        String selectedSubcategory = subcategories.get(subcategoryChoice);
        
        // Step 3: Retrieve subsubcategories (if any) based on the selected subcategory
        List<String> subsubcategories = AddActivityDao.getActivitySubsubcategories(activityChoice + 1, selectedSubcategory);
        
        int activity_category_id;
        // Display the subsubcategories if any
        if (!subsubcategories.isEmpty()) {
            System.out.println("Select a Subsubcategory:");
            for (int i = 0; i < subsubcategories.size(); i++) {
                System.out.println((i + 1) + ". " + subsubcategories.get(i));
            }
            int subsubCategoryChoice = getUserChoice(subsubcategories.size()) - 1;
            String selectedSubsubCategory = subsubcategories.get(subsubCategoryChoice);
            activity_category_id = AddActivityDao.getActivityCategoryId(selectedSubcategory, selectedSubsubCategory);
            // Use selectedSubsubCategory later in the emission calculation (if needed)
        } else {
            activity_category_id = AddActivityDao.getActivityCategoryId(selectedSubcategory, null);
        }
        
        // Step 4: Collect input value from the user
        System.out.print("Enter the value for " + AddActivityDao.getInputUnit(selectedCategory) + ": ");
        double input = new Scanner(System.in).nextDouble();
        
        // Step 6: Retrieve emission rate for the selected subcategory
        double emissionRate = AddActivityDao.getEmissionRate(activityChoice + 1, activity_category_id);
        
        // Step 7: Calculate total emission
        double totalEmission = input * emissionRate;
        
        System.out.println("Carbon Footprint of the activity: "+totalEmission+" kgCO2");
    }

    @Override
    public void setSustainabilityGoal(int userId) {
        // Step 1: Fetch the most recent activity date
        LocalDate lastActivityDate = SustainableGoalDao.getLastUserActivityDate(userId);

        // If no activities found, return early
        if (lastActivityDate == null) {
            System.out.println("No activities found for the user.");
            return;
        }

        // Step 2: Calculate the one-week period for activities (last 7 days from last activity)
        LocalDate startDate = lastActivityDate.minus(1, ChronoUnit.WEEKS);

        // Step 3: Fetch the user's activities for the last week
        List<UserActivity> userActivities = SustainableGoalDao.getUserActivitiesForDateRange(userId, startDate, lastActivityDate);

        // If no activities are found in the last week, return early
        if (userActivities.isEmpty()) {
            System.out.println("No activities found for the last week.");
            return;
        }

        // Step 4: Calculate total emissions for each category and overall
        Map<String, BigDecimal> categoryEmissions = new HashMap<>();
        BigDecimal totalEmissions = BigDecimal.ZERO;

        // Categorize emissions by activity type
        for (UserActivity activity : userActivities) {
            String category = activity.getActivityCategory();
            BigDecimal activityEmission = activity.getTotalEmission();

            categoryEmissions.put(category, categoryEmissions.getOrDefault(category, BigDecimal.ZERO).add(activityEmission));
            totalEmissions = totalEmissions.add(activityEmission);
        }

        // Display the emissions per category
        System.out.println("Sustainability Goals for User " + userId + ":");
        boolean exceededLimit = false;

        // Set thresholds for each category (these can be adjusted as per the requirements)
        BigDecimal transportationThreshold = new BigDecimal("150.00");  // Example threshold for Transportation category
        BigDecimal energyThreshold = new BigDecimal("230.00");  // Example threshold for Energy category
        BigDecimal wasteThreshold = new BigDecimal("200.00");  // Example threshold for Waste category

        // Check if the emissions exceed the threshold and suggest reductions
        if (categoryEmissions.containsKey("Transportation") && categoryEmissions.get("Transportation").compareTo(transportationThreshold) > 0) {
            BigDecimal excessEmissions = categoryEmissions.get("Transportation").subtract(transportationThreshold);
            BigDecimal percentageReduction = (excessEmissions.divide(transportationThreshold, 2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100"));
            System.out.println("Reduce Transportation emission by " + percentageReduction + "%");
            exceededLimit = true;
        }

        if (categoryEmissions.containsKey("Energy Use") && categoryEmissions.get("Energy Use").compareTo(energyThreshold) > 0) {
            BigDecimal excessEmissions = categoryEmissions.get("Energy Use").subtract(energyThreshold);
            BigDecimal percentageReduction = (excessEmissions.divide(energyThreshold, 2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100"));
            System.out.println("Reduce Energy emission by " + percentageReduction + "%");
            exceededLimit = true;
        }

        if (categoryEmissions.containsKey("Waste Disposal") && categoryEmissions.get("Waste Disposal").compareTo(wasteThreshold) > 0) {
            BigDecimal excessEmissions = categoryEmissions.get("Waste Disposal").subtract(wasteThreshold);
            BigDecimal percentageReduction = (excessEmissions.divide(wasteThreshold, 2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100"));
            System.out.println("Reduce Waste emission by " + percentageReduction + "%");
            exceededLimit = true;
        }

        // Check if the total emission exceeded a global threshold
        BigDecimal overallThreshold = new BigDecimal("500.00");  // Example overall emission threshold
        if (totalEmissions.compareTo(overallThreshold) > 0) {
            BigDecimal excessEmissions = totalEmissions.subtract(overallThreshold);
            BigDecimal percentageReduction = (excessEmissions.divide(overallThreshold, 2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100"));
            System.out.println("Reduce Overall emission by " + percentageReduction + "%");
            exceededLimit = true;
        }

        // If no limits were exceeded, encourage the user
        if (!exceededLimit) {
            System.out.println("No limit exceeded. Good job!");
        }
    }
}



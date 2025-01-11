package com.amdocs.cet.test;

import com.amdocs.cet.bean.User;
import com.amdocs.cet.service.UserServiceImpl;
import com.amdocs.cet.service.UserActivityServiceImpl; // Assuming you will have a UserActivity service implementation

import java.util.Scanner;

public class MainClass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserServiceImpl userService = new UserServiceImpl();
        UserActivityServiceImpl userActivityService = new UserActivityServiceImpl(); // Create service for activity-related operations

        while (true) {
            // Display menu options for login/register
        	System.out.println("=========================================");
            System.out.println("===>           Welcome to The        <===");
            System.out.println("===>  Carbon Emission Tracker System <===");
            System.out.println("=========================================");
            System.out.println("\nPlease choose one of the following options:\n");
            System.out.println("**************************************");
            System.out.println("*                                    *");
            System.out.println("*             1. REGISTER            *");
            System.out.println("*             2. LOGIN               *");
            System.out.println("*             3. EXIT                *");
            System.out.println("*                                    *");
            System.out.println("**************************************");

            System.out.print("Enter your choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left by nextInt()

            switch (choice) {
                case 1:
                    // User Registration
                    System.out.println("Enter username:");
                    String username = scanner.nextLine();

                    // Secure password input using custom masking method
                    System.out.println("Enter password:");
                    String password = scanner.nextLine();
                    // Validate password
                    if (!isValidPassword(password)) {
                        System.out.println("Password does not meet the required conditions.");
                        break;
                    }

                    System.out.println("Enter full name:");
                    String fullname = scanner.nextLine();

                    // Creating a new user for registration
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword(password); // Plain password will be hashed in service
                    newUser.setFullname(fullname);

                    boolean isRegistered = userService.registerUser(newUser);
                    if (isRegistered) {
                        System.out.println("User registered successfully!");
                    } else {
                        System.out.println("User registration failed.");
                    }
                    break;

                case 2:
                    // User Login
                    System.out.println("Enter username:");
                    String loginUsername = scanner.nextLine();

                    // Secure password input using custom masking method
                    System.out.println("Enter password:");
                    String loginPassword = scanner.nextLine();

                    int loggedInUserId = userService.loginUser(loginUsername, loginPassword);
                    if (loggedInUserId != -1) {
                        System.out.println("Login successful!");
                        // After successful login, display the main menu and pass the user ID
                        showMainMenu(scanner, userActivityService, loggedInUserId);
                    } else {
                        System.out.println("Login failed. Invalid username or password.");
                    }
                    break;

                case 3:
                    // Exit the program
                    System.out.println("Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }
        }
    }

    // Main Menu Method after login, now with userId parameter to track the logged-in user
    private static void showMainMenu(Scanner scanner, UserActivityServiceImpl userActivityService, int userId) {
        while (true) {
            // Display main menu options
        	System.out.println("\n===================================================");
        	System.out.println("====>      CARBON EMISSION TRACKER SYSTEM     <====");
        	System.out.println("====>                MAIN MENU                <====");
        	System.out.println("===================================================");

            System.out.println("\nPlease choose one of the following options:\n");
            System.out.println("*************************************************");
            System.out.println("*                                               *");
            System.out.println("*  1. Add activity data                         *");
            System.out.println("*  2. Track emission footprint data             *");
            System.out.println("*  3. Activity-based footprint data             *");
            System.out.println("*  4. Activity-based emission calculator        *");
            System.out.println("*  5. Sustainability goal setting               *");
            System.out.println("*  6. Logout                                    *");
            System.out.println("*                                               *");
            System.out.println("*************************************************");
            System.out.println("=========================================");
            System.out.print("   Enter your choice (1-6): ");

            int choice = scanner.nextInt();
            System.out.println();
            System.out.println();

            switch (choice) {
                case 1:
                    // Add activity data
                    System.out.println("Enter activity details to add: ");
                    // Logic to add activity data (example - get user input and call service)
                    userActivityService.addActivityData(userId);  // Pass userId to associate activity with logged-in user
                    break;

                case 2:
                    // Track emission footprint data
                    System.out.println("Tracking emission footprint:");
                    // Logic to track emission footprint
                    userActivityService.trackEmissionFootprint(userId);  // Assuming service has method for this
                    break;

                case 3:
                    // Activity-based footprint data
                    System.out.println("Fetching activity-based footprint data:");
                    // Logic to get and show activity-based footprint data
                    userActivityService.getActivityBasedFootprintData(userId);  // Assuming service method for this
                    break;

                case 4:
                    // Activity-based emission calculator
                    System.out.println("Calculating emissions:");
                    // Logic to calculate emissions based on activity data
                    userActivityService.calculateActivityBasedEmission(userId);  // Assuming service method for this
                    break;

                case 5:
                    // Sustainability goal setting
                    System.out.println("Setting sustainability goals:");
                    // Logic for sustainability goal setting
                    userActivityService.setSustainabilityGoal(userId);  // Assuming service method for this
                    break;

                case 6:
                    // Logout
                    System.out.println("Logging out...");
                    return;  // Return to login/register menu

                default:
                    System.out.println("Invalid choice, please try again.");
                    break;
            }

            // Ask user to press Enter to continue after each operation
            waitForUserToContinue(scanner);
        }
    }

    // Method to wait for the user to press Enter before continuing
    private static void waitForUserToContinue(Scanner scanner) {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();  // Waits for the user to press Enter
        scanner.nextLine();
    }

    // Password validation method
    private static boolean isValidPassword(String password) {
        if (password.length() < 8) {
            System.out.println("Password must be at least 8 characters long.");
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        // Check each character for validation
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUppercase = true;
            else if (Character.isLowerCase(c)) hasLowercase = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecialChar = true;
        }

        if (!hasUppercase) {
            System.out.println("Password must contain at least one uppercase letter.");
        }
        if (!hasLowercase) {
            System.out.println("Password must contain at least one lowercase letter.");
        }
        if (!hasDigit) {
            System.out.println("Password must contain at least one digit.");
        }
        if (!hasSpecialChar) {
            System.out.println("Password must contain at least one special character.");
        }

        // Return true if all conditions are met
        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar;
    }
}

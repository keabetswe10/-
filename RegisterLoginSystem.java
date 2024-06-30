/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.register_login1;
import javax.swing.JOptionPane;
/**
 *
 * @author Keabetswe malwa
 */

public class RegisterLoginSystem {
    private static String firstname;
    private static String lastname;
    private static String username;
    private static String password;

    // Maximum length for the username
    private static final int MAX_USERNAME_LENGTH = 20;
    private static int taskNumber = 0; // Add taskNumber variable

    // Arrays for task details
    private static String[] developers;
    private static String[] taskNames;
    private static String[] taskIDs;
    private static int[] taskDurations;
    private static String[] taskStatuses;

    public static void main(String[] args) {
        // Register a new user
        register();

        // Login with registered credentials
        login();
    }

    private static void register() {
        firstname = JOptionPane.showInputDialog("Enter a firstname:");
        lastname = JOptionPane.showInputDialog("Enter a lastname:");
        username = JOptionPane.showInputDialog("Enter a username (Please ensure that your username is not more that 5 characters long , must contain an underscore):");
        password = JOptionPane.showInputDialog("Create a password:");

        // Check if any field is empty
        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all the required fields");
            register(); // Allows the user to input all fields that are required
        } else {
            // Check username length and format
            if (!checkUserName(username)) {
                JOptionPane.showMessageDialog(null, "Username is not correctly formatted. Please ensure that the username contains an underscore and is no more than 5 characters in length.");
                register(); // Repeat for username
            } else {
                // Check password strength
                if (!meetsPasswordComplexity(password)) {
                    JOptionPane.showMessageDialog(null, "Password is not correctly formatted. Please ensure that the password contains at least 8 characters, a capital letter, a number, and a special character.");
                    register(); // Re-prompt for password
                } else {
                    JOptionPane.showMessageDialog(null, "Registration successful. You can now login.");
                }
            }
        }
    }

    private static void login() {
        String inputUsername = JOptionPane.showInputDialog("Enter your username:");
        String inputPassword = JOptionPane.showInputDialog("Enter your password:");

        if (inputUsername.equals(username) && inputPassword.equals(password)) {
            JOptionPane.showMessageDialog(null, "Login successful!");
            JOptionPane.showMessageDialog(null, "Welcome to EasyKanban " + inputUsername + "!");

            // Get the number of tasks from the user
            int numTasks = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of tasks:"));

            // Initialize arrays based on the number of tasks
            developers = new String[numTasks];
            taskNames = new String[numTasks];
            taskIDs = new String[numTasks];
            taskDurations = new int[numTasks];
            taskStatuses = new String[numTasks];

            // Loop until the user chooses to quit
            while (true) {
                // Provide user with options
                String[] options = {"Add task", "Show report", "Delete task", "Search task by name", "Search tasks by developer", "Quit"};
                int choice = JOptionPane.showOptionDialog(null, "Please choose an option:", "Welcome!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                switch (choice) {
                    case 0:
                        // Add task option selected
                        addTask("Create Login", "Create login to authenticate users");
                        break;
                    case 1:
                        // Show report option selected
                        showReport();
                        break;
                    case 2:
                        // Delete task option selected
                        String taskToDelete = JOptionPane.showInputDialog(null, "Enter the task name to delete:");
                        deleteTaskByName(taskToDelete);
                        break;
                    case 3: // Assuming 3 is the index for "Search task" in your options array
                        String taskToSearch = JOptionPane.showInputDialog("Enter the task name to search:");
                        searchTaskByName(taskToSearch);
                        break;
                    case 4: // Assuming 4 is the index for "Search tasks by developer" in your options array
                        String developerToSearch = JOptionPane.showInputDialog("Enter the developer's name to search:");
                        searchTasksByDeveloper(developerToSearch);
                        break;
                    case 5:
                        // Quit option selected
                        quit();
                        break;
                    default:
                        // This should not occur
                        JOptionPane.showMessageDialog(null, "Invalid option selected");
                        break;

                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Incorrect username or password. Please try again.");
            login(); // Recursive call for reattempting login
        }
    }

    private static void addTask(String create_Login, String create_login_to_authenticate_users) {
        // Increment task number
        taskNumber++;

        // Ask the user to enter task details
        String taskName = JOptionPane.showInputDialog("Enter the name of the task:");
        String taskID = generateTaskID();
        String taskDescription = JOptionPane.showInputDialog("Enter task description (50 characters or less):");
        // Check if Description is more than 50 characters
        if (taskDescription.length() > 50) {
            JOptionPane.showMessageDialog(null, "Please enter a task description of less than 50 characters");
            addTask("Create Login", "Create login to authenticate users");// Allows the user to Re-enter the required
        }
        String developerFirstName = JOptionPane.showInputDialog("Enter developer's first name:");
        String developerLastName = JOptionPane.showInputDialog("Enter developer's last name:");
        String developerDetails = developerFirstName + " " + developerLastName;
        int taskDuration = Integer.parseInt(JOptionPane.showInputDialog("Enter task duration in hours:"));

        // Menu for selecting task status
        String[] statusOptions = {"To Do", "Doing", "Done"};
        int statusChoice = JOptionPane.showOptionDialog(null, "Select task status:", "Task Status", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, statusOptions, statusOptions[0]);
        String taskStatus;
        switch (statusChoice) {
            case 0:
                taskStatus = "To Do";
                break;
            case 1:
                taskStatus = "Doing";
                break;
            case 2:
                taskStatus = "Done";
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid status selected. Setting status to To Do.");
                taskStatus = "To Do"; // Default status
                break;
        }

        // Store task details in arrays
        developers[taskNumber - 1] = developerDetails;
        taskNames[taskNumber - 1] = taskName;
        taskIDs[taskNumber - 1] = taskID;
        taskDurations[taskNumber - 1] = taskDuration;
        taskStatuses[taskNumber - 1] = taskStatus;

        // Display task details
        JOptionPane.showMessageDialog(null, "Task captured successfully:\n" +
                "Task Name: " + taskName + "\n" +
                "Task ID: " + taskID + "\n" +
                "Task Description: " + taskDescription + "\n" +
                "Developer Details: " + developerDetails + "\n" +
                "Task Duration: " + taskDuration + " hours\n" +
                "Task Status: " + taskStatus);
    }

    private static void showReport() {
        StringBuilder report = new StringBuilder();
        report.append("Task Report:\n\n");

        // Iterate through all tasks
        for (int i = 0; i < taskNumber; i++) {
            report.append("Task ").append(i + 1).append(":\n");
            report.append("Task Name: ").append(taskNames[i]).append("\n");
            report.append("Task ID: ").append(taskIDs[i]).append("\n");
            report.append("Developer: ").append(developers[i]).append("\n");
            report.append("Task Duration: ").append(taskDurations[i]).append(" hours\n");
            report.append("Task Status: ").append(taskStatuses[i]).append("\n\n");
        }

        // Display the report in a JOptionPane message dialog
        JOptionPane.showMessageDialog(null, report.toString(), "Task Report", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void searchTaskByName(String name) {
        boolean found = false;

        // Iterate through tasks to find the task with the specified name
        for (int i = 0; i < taskNumber; i++) {
            if (name.equalsIgnoreCase(taskNames[i])) { // Case-insensitive comparison
                JOptionPane.showMessageDialog(null,
                        "Task Name: " + taskNames[i] + "\n" +
                                "Developer: " + developers[i] + "\n" +
                                "Task Status: " + taskStatuses[i]);
                found = true;
                break; // Stop searching once the task is found
            }

        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Task with name '" + name + "' not found.");
        }
    }

    public static void deleteTaskByName(String taskName) {
        boolean found = false;

        // Iterate through tasks to find the task with the specified name
        for (int i = 0; i < taskNumber; i++) {
            if (taskName.equalsIgnoreCase(taskNames[i])) { // Case-insensitive comparison
                // Shift elements to remove the task from arrays
                for (int j = i; j < taskNumber - 1; j++) {
                    developers[j] = developers[j + 1];
                    taskNames[j] = taskNames[j + 1];
                    taskIDs[j] = taskIDs[j + 1];
                    taskDurations[j] = taskDurations[j + 1];
                    taskStatuses[j] = taskStatuses[j + 1];
                }
                // Clear the last element in arrays
                developers[taskNumber - 1] = null;
                taskNames[taskNumber - 1] = null;
                taskIDs[taskNumber - 1] = null;
                taskDurations[taskNumber - 1] = 0;
                taskStatuses[taskNumber - 1] = null;

                taskNumber--; // Decrement task count
                JOptionPane.showMessageDialog(null, "Task '" + taskName + "' deleted successfully.");
                found = true;
                break; // Stop searching once the task is deleted
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Task with name '" + taskName + "' not found.");
        }
    }

    private static String generateTaskID() {
        return "TASK" + taskNumber;
    }

    private static boolean meetsPasswordComplexity(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowerCase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(ch)) {
                hasSpecialChar = true;
            }
        }

        return password.length() >= 8 && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar;
    }

    private static void searchTasksByDeveloper(String developer) {
        boolean found = false;

        // Iterate through tasks to find tasks assigned to the specified developer
        for (int i = 0; i < taskNumber; i++) {
            if (developer.equalsIgnoreCase(developers[i])) { // Case-insensitive comparison
                JOptionPane.showMessageDialog(null,
                        "Task Name: " + taskNames[i] + "\n" +
                                "Task ID: " + taskIDs[i] + "\n" +
                                "Task Duration: " + taskDurations[i] + " hours\n" +
                                "Task Status: " + taskStatuses[i]);
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "No tasks found for developer '" + developer + "'.");
        }
    }

    private static void quit() {
        JOptionPane.showMessageDialog(null, "Thank you for using EasyKanban! Goodbye.");
        System.exit(0);
    }

    private static boolean checkUserName(String username) {
        // Check if username contains an underscore and is no more than 5 characters long
        return username.contains("_") && username.length() <= 5;
    }
} 
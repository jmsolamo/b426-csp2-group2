package com.joysistvi.prestigecliapp.view;

import com.joysistvi.prestigecliapp.model.User;
import com.joysistvi.prestigecliapp.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {

    private final UserService userService;
    private final Scanner scanner;

    public UserView(UserService userService, Scanner scanner) {
        this.userService = userService;
        this.scanner = scanner;
    }

    public User showLoginPrompt() {
        System.out.println("\n----- User Login -----");
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = userService.login(username, password);
        if (user != null) {
            System.out.println("\nLogin successful! Welcome, " + user.getFullName()
                    + " (" + user.getRole() + ")");
        }
        return user;
    }

    public boolean showRegisterPrompt() {
        System.out.println("\n----- Member Registration -----");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password (min 6 chars): ");
        String password = scanner.nextLine();
        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("Registration failed: Passwords do not match.");
            return false;
        }

        boolean success = userService.registerMember(firstName, lastName, username, email, password);
        if (success) {
            System.out.println("Registration successful! You can now log in.");
        }
        return success;
    }

    public void handleAdminUserMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n========================================");
            System.out.println("         MANAGE ALL USERS");
            System.out.println("========================================");
            System.out.println("1. View All Active Users");
            System.out.println("2. View All Archived Users");
            System.out.println("3. Search Users");
            System.out.println("4. Archive User");
            System.out.println("5. Restore User");
            System.out.println("6. Delete User");
            System.out.println("0. Back");

            int choice = promptForInt("Enter your choice: ");
            switch (choice) {
                case 1 -> displayUsers("Active Users", userService.getAllActiveUsers());
                case 2 -> displayUsers("Archived Users", userService.getAllArchivedUsers());
                case 3 -> promptSearchUsers();
                case 4 -> promptArchiveUser();
                case 5 -> promptRestoreUser();
                case 6 -> promptDeleteUser();
                case 0 -> { return; }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public void promptCreateInstructor() {
        System.out.println("\n----- Create Instructor Account -----");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Initial Password (min 6 chars): ");
        String password = scanner.nextLine();

        if (userService.createInstructor(firstName, lastName, username, email, password)) {
            System.out.println("Instructor account created successfully.");
        } else {
            System.out.println("Failed to create instructor account.");
        }
        pressEnterToContinue();
    }

    public void showPersonalDetails(User currentUser) {
        User user = userService.findById(currentUser.getId());
        if (user == null) {
            System.out.println("Error: Could not load your details.");
            pressEnterToContinue();
            return;
        }

        System.out.println("\n----- Personal Details -----");
        System.out.println("Name       : " + user.getFullName());
        System.out.println("Username   : " + user.getUsername());
        System.out.println("Email      : " + user.getEmail());
        System.out.println("Role       : " + user.getRole());
        System.out.println("Member Since: " + user.getCreatedAt());

        System.out.println("\n1. Update Personal Info");
        System.out.println("0. Back");
        int choice = promptForInt("Enter your choice: ");

        if (choice == 1) {
            promptUpdatePersonalInfo(user);
        }
    }

    public void promptChangePassword(User currentUser) {
        System.out.println("\n----- Change Password -----");
        System.out.print("Enter Current Password: ");
        String currentPassword = scanner.nextLine();
        System.out.print("Enter New Password (min 6 chars): ");
        String newPassword = scanner.nextLine();
        System.out.print("Confirm New Password: ");
        String confirmPassword = scanner.nextLine();

        if (!newPassword.equals(confirmPassword)) {
            System.out.println("Error: Passwords do not match.");
            pressEnterToContinue();
            return;
        }

        if (userService.changePassword(currentUser.getId(), currentPassword, newPassword)) {
            System.out.println("Password changed successfully.");
        } else {
            System.out.println("Failed to change password.");
        }
        pressEnterToContinue();
    }

    private void promptUpdatePersonalInfo(User user) {
        System.out.println("\n----- Update Personal Info -----");
        System.out.print("First Name [" + user.getFirstName() + "]: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name [" + user.getLastName() + "]: ");
        String lastName = scanner.nextLine();
        System.out.print("Email [" + user.getEmail() + "]: ");
        String email = scanner.nextLine();

        if (!firstName.trim().isEmpty()) user.setFirstName(firstName.trim());
        if (!lastName.trim().isEmpty())  user.setLastName(lastName.trim());
        if (!email.trim().isEmpty())     user.setEmail(email.trim());

        if (userService.updateUser(user)) {
            System.out.println("Personal info updated successfully.");
        } else {
            System.out.println("Failed to update personal info.");
        }
        pressEnterToContinue();
    }

    private void displayUsers(String title, List<User> users) {
        List<String[]> rows = new ArrayList<>();
        for (User u : users) {
            rows.add(new String[]{
                    String.valueOf(u.getId()), u.getFullName(),
                    u.getUsername(), u.getEmail(), u.getRole()
            });
        }
        pressEnterToContinue();
    }

    private void promptSearchUsers() {
        System.out.print("\nEnter search keyword: ");
        String keyword = scanner.nextLine();
        List<User> results = userService.searchUsers(keyword);
        displayUsers("Search Results", results);
    }

    private void promptArchiveUser() {
        int id = promptForInt("\nEnter User ID to archive: ");
        System.out.print("Are you sure? (Y/N): ");
        String confirm = scanner.nextLine();
        if ("Y".equalsIgnoreCase(confirm)) {
            if (userService.archiveUser(id)) {
                System.out.println("User archived successfully.");
            } else {
                System.out.println("Failed to archive user.");
            }
        } else {
            System.out.println("Archive cancelled.");
        }
        pressEnterToContinue();
    }

    private void promptRestoreUser() {
        int id = promptForInt("\nEnter User ID to restore: ");
        if (userService.restoreUser(id)) {
            System.out.println("User restored successfully.");
        } else {
            System.out.println("Failed to restore user.");
        }
        pressEnterToContinue();
    }

    private void promptDeleteUser() {
        int id = promptForInt("\nEnter User ID to delete: ");
        System.out.print("Are you sure? This cannot be undone. (Y/N): ");
        String confirm = scanner.nextLine();
        if ("Y".equalsIgnoreCase(confirm)) {
            if (userService.deleteUser(id)) {
                System.out.println("User deleted successfully.");
            } else {
                System.out.println("Failed to delete user.");
            }
        } else {
            System.out.println("Delete cancelled.");
        }
        pressEnterToContinue();
    }

    private int promptForInt(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine();
            System.out.print(prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }

    private void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

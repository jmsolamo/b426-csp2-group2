package com.joysistvi.prestigecliapp.view;

import java.util.Scanner;

public class MembershipPlanView {

    private final Scanner scanner;

    public MembershipPlanView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void handleMembershipPlanMenu() {

        while (true) {

            System.out.println("\n===== MEMBERSHIP PLAN MENU =====");
            System.out.println("1. View Plans");
            System.out.println("2. Add Plan");
            System.out.println("3. Update Plan");
            System.out.println("4. Delete Plan");
            System.out.println("0. Back");

            int choice = promptForInt("Choice: ");

            switch (choice) {
                case 1 -> viewPlans();
                case 2 -> addPlan();
                case 3 -> updatePlan();
                case 4 -> deletePlan();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void viewPlans() {
        System.out.println("View Plans");
    }

    private void addPlan() {
        System.out.println("Add Plan");
    }

    private void updatePlan() {
        System.out.println("Update Plan");
    }

    private void deletePlan() {
        System.out.println("Delete Plan");
    }

    private int promptForInt(String prompt) {

        System.out.print(prompt);

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input.");
            scanner.nextLine();
            System.out.print(prompt);
        }

        int choice = scanner.nextInt();
        scanner.nextLine();

        return choice;
    }
}
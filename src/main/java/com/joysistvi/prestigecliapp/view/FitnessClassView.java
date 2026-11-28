package com.joysistvi.prestigecliapp.view;

import java.util.Scanner;

public class FitnessClassView {

    private final Scanner scanner;

    public FitnessClassView(Scanner scanner) {
        this.scanner = scanner;
    }

    public void handleFitnessClassMenu() {

        while (true) {

            System.out.println("\n===== FITNESS CLASS MENU =====");
            System.out.println("1. View Classes");
            System.out.println("2. Create Class");
            System.out.println("3. Update Class");
            System.out.println("4. Delete Class");
            System.out.println("0. Back");

            int choice = promptForInt("Choice: ");

            switch (choice) {

                case 1 -> viewClasses();

                case 2 -> createClass();

                case 3 -> updateClass();

                case 4 -> deleteClass();

                case 0 -> {
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void viewClasses() {
        System.out.println("View Classes");
    }

    private void createClass() {
        System.out.println("Create Class");
    }

    private void updateClass() {
        System.out.println("Update Class");
    }

    private void deleteClass() {
        System.out.println("Delete Class");
    }

    private int promptForInt(String prompt) {

        System.out.print(prompt);

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input.");
            scanner.nextLine();
            System.out.print(prompt);
        }

        int value = scanner.nextInt();
        scanner.nextLine();

        return value;
    }
}
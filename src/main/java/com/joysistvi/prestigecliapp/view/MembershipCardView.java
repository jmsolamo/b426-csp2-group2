package com.prestigegym.view;

import java.util.Scanner;

public class MembershipCardView {

    private static final Scanner scanner = new Scanner(System.in);

    public static void menu() {

        int choice;

        do {
            System.out.println("\n===== MEMBERSHIP CARD MENU =====");
            System.out.println("1. View Membership Cards");
            System.out.println("2. Generate Membership Card");
            System.out.println("3. Search Membership Card");
            System.out.println("0. Back");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Viewing membership cards...");
                    break;
                case 2:
                    System.out.println("Generating membership card...");
                    break;
                case 3:
                    System.out.println("Searching membership card...");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }
}
package com.joysistvi.prestigecliapp.view;

import com.joysistvi.prestigecliapp.model.SubscribedMember;
import com.joysistvi.prestigecliapp.service.SubscribedMemberService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SubscribedMemberView {

    private final SubscribedMemberService smService;
    private final Scanner scanner;

    public SubscribedMemberView(SubscribedMemberService smService, Scanner scanner) {
        this.smService = smService;
        this.scanner = scanner;
    }

    public void handleMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n========================================");
            System.out.println("     SUBSCRIBED MEMBERS MANAGEMENT");
            System.out.println("========================================");
            System.out.println();
            System.out.println("  [1] View All Active Subscribed Members");
            System.out.println("  [2] Add Subscribed Member");
            System.out.println("  [3] Update Subscription Status");
            System.out.println("  [4] View Subscribed Members by User ID");
            System.out.println("  [5] View Subscribed Members by Plan ID");
            System.out.println("  [6] Archive Subscribed Member");
            System.out.println("  [7] Restore Subscribed Member");
            System.out.println("  [8] Delete Subscribed Member");
            System.out.println();
            System.out.println("  [0] Back");
            System.out.println();
            System.out.println("----------------------------------------");

            int choice = promptForInt("Enter your choice: ");

            switch (choice) {
                case 1 -> displayAll();
                case 2 -> promptAdd();
                case 3 -> promptUpdateStatus();
                case 4 -> promptFindByUserId();
                case 5 -> promptFindByMembershipPlanId();
                case 6 -> promptArchive();
                case 7 -> promptRestore();
                case 8 -> promptDelete();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }

            pressEnterToContinue();
        }
    }

    public void displayAll() {
        List<SubscribedMember> subscribedMembers = new ArrayList<>();

        for (SubscribedMember member : smService.findAll()) {
            if (!member.isArchived()) {
                subscribedMembers.add(member);
            }
        }

        printTable("----- Active Subscribed Members -----", subscribedMembers,
                "No subscribed members found.");
    }

    private void promptAdd() {
        int userId = promptForInt("Enter user ID: ");
        int membershipPlanId = promptForInt("Enter membership plan ID: ");

        System.out.print("Enter start date (YYYY-MM-DD): ");
        Date startDate = promptForDate();

        System.out.print("Enter expiration date (YYYY-MM-DD): ");
        Date expirationDate = promptForDate();

        System.out.print("Enter subscription status: ");
        String status = scanner.nextLine();

        SubscribedMember subscribedMember = new SubscribedMember(
                userId,
                membershipPlanId,
                startDate,
                expirationDate,
                status
        );

        if (smService.add(subscribedMember)) {
            System.out.println("Subscribed member added successfully!");
        } else {
            System.out.println("Failed to add subscribed member.");
        }
    }

    private void promptUpdateStatus() {
        displayAll();

        int id = promptForInt("Enter subscribed member ID: ");

        System.out.print("Enter new subscription status: ");
        String status = scanner.nextLine();

        if (smService.updateStatus(id, status)) {
            System.out.println("Subscription status updated successfully!");
        } else {
            System.out.println("Failed to update subscription status.");
        }
    }

    private void promptFindByUserId() {
        int userId = promptForInt("Enter user ID: ");

        printTable("----- Subscribed Members by User ID -----",
                smService.findByUserId(userId),
                "No subscribed members found.");
    }

    private void promptFindByMembershipPlanId() {
        int membershipPlanId = promptForInt("Enter membership plan ID: ");

        printTable("----- Subscribed Members by Membership Plan -----",
                smService.findByMembershipPlanId(membershipPlanId),
                "No subscribed members found.");
    }

    private void promptArchive() {
        displayAll();

        int id = promptForInt("Enter subscribed member ID to archive: ");

        System.out.print("Confirm archive ID " + id + "? (Y/N): ");

        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
            System.out.println("Archive cancelled.");
            return;
        }

        if (smService.archive(id)) {
            System.out.println("Subscribed member archived.");
        } else {
            System.out.println("Failed to archive subscribed member.");
        }
    }

    private void promptRestore() {
        List<SubscribedMember> archived = new ArrayList<>();

        for (SubscribedMember member : smService.findAll()) {
            if (member.isArchived()) {
                archived.add(member);
            }
        }

        printTable("----- Archived Subscribed Members -----",
                archived,
                "No archived subscribed members found.");

        if (archived.isEmpty()) {
            return;
        }

        int id = promptForInt("Enter subscribed member ID to restore: ");

        if (smService.restore(id)) {
            System.out.println("Subscribed member restored.");
        } else {
            System.out.println("Failed to restore subscribed member.");
        }
    }

    private void promptDelete() {
        displayAll();

        int id = promptForInt("Enter subscribed member ID to delete: ");

        System.out.print("Are you sure? This cannot be undone. (Y/N): ");

        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (smService.remove(id)) {
            System.out.println("Subscribed member deleted.");
        } else {
            System.out.println("Failed to delete subscribed member.");
        }
    }

    private void printTable(String header, List<SubscribedMember> list, String emptyMsg) {
        System.out.println("\n" + header);

        if (list == null || list.isEmpty()) {
            System.out.println(emptyMsg);
            return;
        }

        System.out.printf("%-5s | %-10s | %-15s | %-12s | %-12s | %-12s | %-10s%n",
                "ID", "User ID", "Plan ID", "Start Date", "Expiration", "Status", "Archived");

        System.out.println("-".repeat(100));

        for (SubscribedMember member : list) {
            System.out.printf("%-5d | %-10d | %-15d | %-12s | %-12s | %-12s | %-10s%n",
                    member.getId(),
                    member.getUserId(),
                    member.getMembershipPlanId(),
                    member.getStartDate(),
                    member.getExpirationDate(),
                    member.getStatus(),
                    member.isArchived() ? "Yes" : "No");
        }

        System.out.println("-".repeat(100));
    }

    private Date promptForDate() {
        while (true) {
            try {
                return Date.valueOf(scanner.nextLine().trim());
            } catch (IllegalArgumentException e) {
                System.out.print("Invalid date. Enter YYYY-MM-DD: ");
            }
        }
    }

    private int promptForInt(String msg) {
        while (true) {
            System.out.print(msg);

            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
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
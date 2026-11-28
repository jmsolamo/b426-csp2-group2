package com.joysistvi.prestigecliapp.view;

import com.joysistvi.prestigecliapp.model.PurchaseHistory;
import com.joysistvi.prestigecliapp.service.PurchaseHistoryService;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class PurchaseHistoryView {

    private final PurchaseHistoryService phService;
    private final Scanner scanner;

    public PurchaseHistoryView(PurchaseHistoryService phService, Scanner scanner) {
        this.phService = phService;
        this.scanner = scanner;
    }

    public void handleMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n========================================");
            System.out.println("       PURCHASE HISTORY MANAGEMENT");
            System.out.println("========================================");
            System.out.println();
            System.out.println("  [1] View All Purchase History");
            System.out.println("  [2] Add Purchase History");
            System.out.println("  [3] Search Purchase History");
            System.out.println("  [4] Find by Membership Card ID");
            System.out.println("  [5] Find by Purchase Reference");
            System.out.println("  [6] Find by Purchase Type");
            System.out.println("  [7] Update Purchase Status");
            System.out.println();
            System.out.println("  [0] Back");
            System.out.println();
            System.out.println("----------------------------------------");

            int choice = promptForInt("Enter your choice: ");

            switch (choice) {
                case 1 -> displayAll();
                case 2 -> promptAdd();
                case 3 -> promptSearch();
                case 4 -> promptFindByMembershipCardId();
                case 5 -> promptFindByPurchaseReference();
                case 6 -> promptFindByPurchaseType();
                case 7 -> promptUpdateStatus();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }

            pressEnterToContinue();
        }
    }

    public void displayAll() {
        printTable("----- All Purchase History -----",
                phService.findAll(),
                "No purchase history found.");
    }

    private void promptAdd() {
        int membershipCardId = promptForInt("Enter membership card ID: ");

        System.out.print("Enter purchase reference: ");
        String purchaseReference = scanner.nextLine();

        System.out.print("Enter purchase type: ");
        String purchaseType = scanner.nextLine();

        int itemId = promptForInt("Enter item ID: ");
        double amount = promptForDouble("Enter amount: ");

        System.out.print("Enter purchase status: ");
        String status = scanner.nextLine();

        PurchaseHistory purchaseHistory = new PurchaseHistory(
                membershipCardId,
                purchaseReference,
                purchaseType,
                itemId,
                amount,
                status,
                new Timestamp(System.currentTimeMillis())
        );

        if (phService.save(purchaseHistory)) {
            System.out.println("Purchase history added successfully!");
        } else {
            System.out.println("Failed to add purchase history.");
        }
    }

    private void promptSearch() {
        System.out.print("Enter keyword: ");
        String keyword = scanner.nextLine();

        printTable("----- Search Purchase History -----",
                phService.search(keyword),
                "No matching purchase history found.");
    }

    private void promptFindByMembershipCardId() {
        int membershipCardId = promptForInt("Enter membership card ID: ");

        printTable("----- Purchase History by Membership Card ID -----",
                phService.findByMembershipCardId(membershipCardId),
                "No purchase history found.");
    }

    private void promptFindByPurchaseReference() {
        System.out.print("Enter purchase reference: ");
        String purchaseReference = scanner.nextLine();

        PurchaseHistory purchaseHistory =
                phService.findByPurchaseReference(purchaseReference);

        if (purchaseHistory == null) {
            System.out.println("No purchase history found.");
            return;
        }

        printTable("----- Purchase History by Reference -----",
                List.of(purchaseHistory),
                "No purchase history found.");
    }

    private void promptFindByPurchaseType() {
        System.out.print("Enter purchase type: ");
        String purchaseType = scanner.nextLine();

        printTable("----- Purchase History by Type -----",
                phService.findByPurchaseType(purchaseType),
                "No purchase history found.");
    }

    private void promptUpdateStatus() {
        displayAll();

        int id = promptForInt("Enter purchase history ID: ");

        System.out.print("Enter new purchase status: ");
        String status = scanner.nextLine();

        if (phService.updateStatus(id, status)) {
            System.out.println("Purchase status updated successfully!");
        } else {
            System.out.println("Failed to update purchase status.");
        }
    }

    private void printTable(String header, List<PurchaseHistory> list, String emptyMsg) {
        System.out.println("\n" + header);

        if (list == null || list.isEmpty()) {
            System.out.println(emptyMsg);
            return;
        }

        System.out.printf("%-5s | %-12s | %-22s | %-20s | %-8s | %-12s | %-12s%n",
                "ID", "Card ID", "Reference", "Type", "Item ID", "Amount", "Status");

        System.out.println("-".repeat(110));

        for (PurchaseHistory purchase : list) {
            System.out.printf("%-5d | %-12d | %-22s | %-20s | %-8d | %-12.2f | %-12s%n",
                    purchase.getId(),
                    purchase.getMembershipCardId(),
                    purchase.getPurchaseReference(),
                    purchase.getPurchaseType(),
                    purchase.getItemId(),
                    purchase.getAmount(),
                    purchase.getStatus());
        }

        System.out.println("-".repeat(110));
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

    private double promptForDouble(String msg) {
        while (true) {
            System.out.print(msg);

            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. Try again.");
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
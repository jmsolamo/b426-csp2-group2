package com.joysistvi.prestigecliapp.view;

import com.joysistvi.prestigecliapp.model.TopUpHistory;
import com.joysistvi.prestigecliapp.service.TopUpHistoryService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TopUpHistoryView {
    private final TopUpHistoryService topupService;
    private final Scanner scanner;

    public TopUpHistoryView(TopUpHistoryService topupService, Scanner scanner) {
        this.topupService = topupService;
        this.scanner = scanner;
    }

    public void handleAdminMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n========================================");
            System.out.println("       TOP-UP HISTORY MANAGEMENT");
            System.out.println("========================================");
            System.out.println();
            System.out.println("  [1] View All Top-Up Records");
            System.out.println("  [2] View Pending Top-Ups");
            System.out.println("  [3] Complete Pending Top-Up (Verify & Credit Card)");
            System.out.println("  [4] Mark Pending Top-Up as FAILED");
            System.out.println("  [5] Refund Completed Top-Up");
            System.out.println("  [6] Delete Top-Up Record");
            System.out.println();
            System.out.println("  [0] Back");
            System.out.println();
            System.out.println("----------------------------------------");

            int choice = promptForInt("Enter your choice: ");
            switch (choice) {
                case 1 -> displayAll();
                case 2 -> displayPending();
                case 3 -> promptCompleteTopup();
                case 4 -> promptFailTopup();
                case 5 -> promptRefundTopup();
                case 6 -> promptDelete();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
            pressEnterToContinue();
        }
    }

    private void displayPending() {
        printTable("----- Pending Top-Up Requests -----", topupService.getPending(), "No pending top-ups.");
    }

    private void promptFailTopup() {
        displayPending();
        int id = promptForInt("Enter Top-Up ID to mark FAILED: ");
        if (topupService.failTopup(id)) {
            System.out.println("Top-up marked FAILED.");
        }
    }

    private void promptRefundTopup() {
        displayAll();
        int id = promptForInt("Enter Top-Up ID to refund: ");
        if (topupService.refundTopup(id)) {
            System.out.println("Top-up refunded and card balance deducted.");
        }
    }

    private void promptDelete() {
        displayAll();
        int id = promptForInt("Enter Top-Up ID to delete: ");
        System.out.print("Are you sure? This cannot be undone. (Y/N): ");
        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
            System.out.println("Delete cancelled.");
        } else if (topupService.delete(id)) {
            System.out.println("Top-up record deleted.");
        }
    }

    private void promptCompleteTopup() {
        List<TopUpHistory> pending = topupService.getPending();
        printTable("----- Pending Top-Up Requests -----", pending, "No pending top-ups.");
        if (pending == null || pending.isEmpty()) return;

        int id = promptForInt("Enter Top-Up ID to complete: ");
        TopUpHistory th = topupService.getById(id);
        if (th == null) {
            System.out.println("Error: Top-up record #" + id + " not found.");
            return;
        }
        if (!"PENDING".equalsIgnoreCase(th.getStatus())) {
            System.out.println("Error: Top-up #" + id + " is not PENDING (status: " + th.getStatus() + ").");
            return;
        }

        BigDecimal amt = th.getAmount() != null ? th.getAmount() : BigDecimal.ZERO;
        System.out.println("\n----- Top-Up Verification Details -----");
        System.out.println("Member                : " + (th.getMemberName() != null ? th.getMemberName() : "-"));
        System.out.println("Card No               : " + (th.getCardNo() != null ? th.getCardNo() : "-"));
        System.out.println("Topup Ref             : " + th.getTopupReference());
        System.out.println("Payment Method        : " + th.getPaymentMethod());
        System.out.printf("Amount                : PHP %.2f%n", amt);
        System.out.println("Submitted Payment Ref : " + (th.getPaymentReference() != null && !th.getPaymentReference().isBlank() ? th.getPaymentReference() : "(none)"));

        String expectedRef = th.getPaymentReference() != null ? th.getPaymentReference().trim() : "";
        if (!expectedRef.isEmpty()) {
            System.out.print("Re-enter payment reference to verify: ");
            String enteredRef = scanner.nextLine().trim();
            if (!expectedRef.equalsIgnoreCase(enteredRef)) {
                System.out.println("Verification failed: Payment reference does not match. Top-up remains PENDING.");
                return;
            }
        } else {
            System.out.printf("Confirm complete top-up and credit PHP %.2f to card? (Y/N): ", amt);
            if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
                System.out.println("Top-up completion cancelled.");
                return;
            }
        }

        if (topupService.completeTopup(id)) {
            System.out.println("Top-up verified, completed, and membership card credited!");
        }
    }

    public void displayAll() {
        printTable("----- Top-Up History -----", topupService.getAll(), "No top-up records found.");
    }

    public void displayUserTopups(int userId) {
        printTable("----- My Top-Up History -----", topupService.getByUserId(userId), "No top-up history found.");
    }

    public void promptSubmitTopup(int userId) {
        System.out.println("\n----- Submit Wallet Top-Up -----");
        System.out.print("Payment Method (E_WALLET / BANK_TRANSFER): ");
        String method = scanner.nextLine();
        System.out.print("Payment Reference (e.g. GCASH-12345 / BPI-9876): ");
        String payRef = scanner.nextLine();
        BigDecimal amount = promptForDecimal("Enter top-up amount (PHP): ");

        if (topupService.submitTopup(userId, method, payRef, amount)) {
            System.out.println("Top-up request submitted (status: PENDING admin verification)!");
        } else {
            System.out.println("Failed to submit top-up.");
        }
    }

    private void printTable(String header, List<TopUpHistory> list, String emptyMsg) {
        List<String[]> rows = new ArrayList<>();
        if (list != null) {
            for (TopUpHistory th : list) {
                BigDecimal amt = th.getAmount() != null ? th.getAmount() : BigDecimal.ZERO;
                rows.add(new String[]{
                        String.valueOf(th.getId()),
                        th.getTopupReference(),
                        th.getPaymentReference() != null ? th.getPaymentReference() : "-",
                        th.getMemberName(),
                        th.getCardNo(),
                        String.format("%.2f", amt),
                        th.getPaymentMethod(),
                        th.getStatus()
                });
            }
        }
        printFormattedTable(
                header,
                new String[]{"ID", "Topup Ref", "Payment Ref", "Member", "Card No", "Amount", "Method", "Status"},
                new int[]{3, 18, 16, 16, 14, 9, 13, 10},
                rows,
                emptyMsg
        );
    }

    private void printFormattedTable(String title, String[] headers, int[] widths, List<String[]> rows, String emptyMessage) {
        System.out.println("\n" + title);
        if (rows == null || rows.isEmpty()) {
            System.out.println(emptyMessage);
            return;
        }
        StringBuilder fmt = new StringBuilder();
        int totalWidth = 0;
        for (int i = 0; i < widths.length; i++) {
            fmt.append("%-").append(widths[i]).append("s");
            totalWidth += widths[i];
            if (i < widths.length - 1) {
                fmt.append(" | ");
                totalWidth += 3;
            }
        }
        String format = fmt.toString();
        String sep = "-".repeat(totalWidth);
        System.out.println(sep);
        Object[] hdrObjs = new Object[headers.length];
        for (int i = 0; i < headers.length; i++) {
            hdrObjs[i] = truncate(headers[i], widths[i]);
        }
        System.out.printf(format + "%n", hdrObjs);
        System.out.println(sep);
        for (String[] row : rows) {
            Object[] cells = new Object[widths.length];
            for (int i = 0; i < widths.length; i++) {
                String val = (row != null && i < row.length) ? row[i] : "";
                cells[i] = truncate(val, widths[i]);
            }
            System.out.printf(format + "%n", cells);
        }
        System.out.println(sep);
    }

    private String truncate(String val, int maxWidth) {
        if (val == null) return "";
        if (val.length() <= maxWidth) return val;
        return maxWidth > 3 ? val.substring(0, maxWidth - 3) + "..." : val.substring(0, maxWidth);
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

    private BigDecimal promptForDecimal(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return new BigDecimal(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid decimal amount. Try again.");
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
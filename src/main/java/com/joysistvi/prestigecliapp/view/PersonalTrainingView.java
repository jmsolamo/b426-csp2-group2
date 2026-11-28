package com.joysistvi.prestigecliapp.view;

import com.joysistvi.prestigecliapp.model.PersonalTraining;
import com.joysistvi.prestigecliapp.service.PersonalTrainingService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersonalTrainingView {
    private final PersonalTrainingService ptService;
    private final Scanner scanner;

    public PersonalTrainingView(PersonalTrainingService ptService, Scanner scanner) {
        this.ptService = ptService;
        this.scanner = scanner;
    }

    public void handleMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n========================================");
            System.out.println("  PERSONAL TRAINING PROGRAMS MANAGEMENT");
            System.out.println("========================================");
            System.out.println();
            System.out.println("  [1] View All Active PT Programs");
            System.out.println("  [2] Create PT Program");
            System.out.println("  [3] Update PT Program");
            System.out.println("  [4] Search PT Programs");
            System.out.println("  [5] Archive PT Program");
            System.out.println("  [6] Restore PT Program");
            System.out.println("  [7] Delete PT Program");
            System.out.println();
            System.out.println("  [0] Back");
            System.out.println();
            System.out.println("----------------------------------------");

            int choice = promptForInt("Enter your choice: ");
            switch (choice) {
                case 1 -> displayAll();
                case 2 -> promptCreate();
                case 3 -> promptUpdate();
                case 4 -> promptSearch();
                case 5 -> promptArchive();
                case 6 -> promptRestore();
                case 7 -> promptDelete();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
            pressEnterToContinue();
        }
    }

    public void displayAll() {
        printTable("----- Active Personal Training Programs -----", ptService.getAllActive(), "No PT programs found.");
    }

    private void promptCreate() {
        int instructorId = promptForInt("Enter instructor user ID: ");
        System.out.print("Enter category / specialization: ");
        String category = scanner.nextLine();
        System.out.print("Enter description: ");
        String desc = scanner.nextLine();
        BigDecimal rate = promptForDecimal("Enter hourly rate (PHP): ");
        if (ptService.create(instructorId, category, desc, rate)) {
            System.out.println("Personal training program created successfully!");
        } else {
            System.out.println("Failed to create PT program.");
        }
    }

    private void promptUpdate() {
        displayAll();
        int id = promptForInt("Enter PT program ID to update: ");
        int instructorId = promptForInt("Enter instructor user ID: ");
        System.out.print("Enter new category: ");
        String category = scanner.nextLine();
        System.out.print("Enter new description: ");
        String desc = scanner.nextLine();
        BigDecimal rate = promptForDecimal("Enter new hourly rate (PHP): ");
        if (ptService.update(id, instructorId, category, desc, rate)) {
            System.out.println("PT program updated successfully!");
        } else {
            System.out.println("Failed to update PT program.");
        }
    }

    private void promptSearch() {
        System.out.print("Enter keyword: ");
        String kw = scanner.nextLine();
        printTable("----- Search PT Programs -----", ptService.search(kw), "No matching PT programs found.");
    }

    private void promptArchive() {
        displayAll();
        int id = promptForInt("Enter PT ID to archive: ");
        System.out.print("Confirm archive PT ID " + id + "? (Y/N): ");
        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
            System.out.println("Archive cancelled.");
            return;
        }
        if (ptService.archive(id)) {
            System.out.println("PT program archived.");
        }
    }

    private void promptRestore() {
        List<PersonalTraining> archived = ptService.getAllArchived();
        printTable("----- Archived PT Programs -----", archived, "No archived PT programs.");
        if (archived == null || archived.isEmpty()) return;
        int id = promptForInt("Enter PT ID to restore: ");
        if (ptService.restore(id)) {
            System.out.println("PT program restored.");
        }
    }

    private void promptDelete() {
        displayAll();
        int id = promptForInt("Enter PT ID to delete: ");
        System.out.print("Are you sure? This cannot be undone. (Y/N): ");
        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
            System.out.println("Delete cancelled.");
            return;
        }
        if (ptService.delete(id)) {
            System.out.println("PT program deleted.");
        }
    }

    private void printTable(String header, List<PersonalTraining> list, String emptyMsg) {
        List<String[]> rows = new ArrayList<>();
        if (list != null) {
            for (PersonalTraining pt : list) {
                BigDecimal rate = pt.getHourlyRate() != null ? pt.getHourlyRate() : BigDecimal.ZERO;
                rows.add(new String[]{
                        String.valueOf(pt.getId()),
                        pt.getInstructorName(),
                        pt.getCategory(),
                        String.format("%.2f", rate),
                        pt.getDescription()
                });
            }
        }
        printFormattedTable(
                header,
                new String[]{"ID", "Instructor", "Category", "Hourly Rate", "Description"},
                new int[]{3, 18, 26, 12, 34},
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
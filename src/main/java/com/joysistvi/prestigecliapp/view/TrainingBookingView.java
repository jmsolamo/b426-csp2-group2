package com.joysistvi.prestigecliapp.view;

import com.joysistvi.prestigecliapp.model.TrainingBooking;
import com.joysistvi.prestigecliapp.service.TrainingBookingService;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TrainingBookingView {
    private final TrainingBookingService bookingService;
    private final PersonalTrainingView ptView;
    private final Scanner scanner;

    public TrainingBookingView(TrainingBookingService bookingService, PersonalTrainingView ptView, Scanner scanner) {
        this.bookingService = bookingService;
        this.ptView = ptView;
        this.scanner = scanner;
    }

    public void handleMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n========================================");
            System.out.println("      TRAINING BOOKINGS MANAGEMENT");
            System.out.println("========================================");
            System.out.println();
            System.out.println("  [1] View All Active Bookings");
            System.out.println("  [2] Update Booking Status");
            System.out.println("  [3] Cancel Booking");
            System.out.println("  [4] Archive Booking");
            System.out.println("  [5] Restore Booking");
            System.out.println("  [6] Delete Booking");
            System.out.println();
            System.out.println("  [0] Back");
            System.out.println();
            System.out.println("----------------------------------------");

            int choice = promptForInt("Enter your choice: ");
            switch (choice) {
                case 1 -> displayAll();
                case 2 -> promptUpdateStatus();
                case 3 -> promptCancel();
                case 4 -> promptArchive();
                case 5 -> promptRestore();
                case 6 -> promptDelete();
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
            pressEnterToContinue();
        }
    }

    public void handleMemberMenu(int userId) {
        while (true) {
            clearScreen();
            System.out.println("\n========================================");
            System.out.println("       PERSONAL TRAINING SESSIONS");
            System.out.println("========================================");
            System.out.println();
            System.out.println("  [1] Browse Personal Training Programs");
            System.out.println("  [2] Book a Personal Training Session");
            System.out.println("  [3] View My Training Bookings");
            System.out.println("  [4] Cancel a Training Booking");
            System.out.println();
            System.out.println("  [0] Back");
            System.out.println();
            System.out.println("----------------------------------------");

            int choice = promptForInt("Enter your choice: ");
            switch (choice) {
                case 1 -> ptView.displayAll();
                case 2 -> promptBookSession(userId);
                case 3 -> displayByUser(userId);
                case 4 -> promptCancelByUser(userId);
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
            pressEnterToContinue();
        }
    }

    public void displayAll() {
        printTable("----- Active Training Bookings -----", bookingService.getAllActive(), "No training bookings found.");
    }

    public void displayByUser(int userId) {
        printTable("----- My Training Bookings -----", bookingService.getByUserId(userId), "You have no training bookings.");
    }

    private void promptBookSession(int userId) {
        ptView.displayAll();
        int ptId = promptForInt("Enter Personal Training Program ID to book: ");
        Date bookingDate = promptForDate("Enter booking date (YYYY-MM-DD): ");
        Time startTime = promptForTime("Enter start time (HH:MM or HH:MM:SS): ");
        Time endTime = promptForTime("Enter end time (HH:MM or HH:MM:SS): ");
        if (bookingService.bookSession(userId, ptId, bookingDate, startTime, endTime)) {
            System.out.println("Personal training session booked successfully!");
        } else {
            System.out.println("Failed to book PT session.");
        }
    }

    private void promptCancelByUser(int userId) {
        displayByUser(userId);
        int id = promptForInt("Enter Booking ID to cancel: ");
        if (bookingService.cancelBooking(id)) {
            System.out.println("Booking cancelled.");
        } else {
            System.out.println("Failed to cancel booking.");
        }
    }

    private void promptUpdateStatus() {
        displayAll();
        int id = promptForInt("Enter booking ID: ");
        System.out.print("Enter new status (PENDING / CONFIRMED / CANCELLED / COMPLETED): ");
        String status = scanner.nextLine();
        if (bookingService.updateStatus(id, status)) {
            System.out.println("Booking status updated.");
        }
    }

    private void promptCancel() {
        displayAll();
        int id = promptForInt("Enter booking ID to cancel: ");
        if (bookingService.cancelBooking(id)) {
            System.out.println("Booking cancelled.");
        }
    }

    private void promptArchive() {
        displayAll();
        int id = promptForInt("Enter booking ID to archive: ");
        System.out.print("Confirm archive booking ID " + id + "? (Y/N): ");
        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
            System.out.println("Archive cancelled.");
            return;
        }
        if (bookingService.archive(id)) {
            System.out.println("Booking archived.");
        }
    }

    private void promptRestore() {
        List<TrainingBooking> archived = bookingService.getAllArchived();
        printTable("----- Archived Training Bookings -----", archived, "No archived bookings.");
        if (archived == null || archived.isEmpty()) return;
        int id = promptForInt("Enter booking ID to restore: ");
        if (bookingService.restore(id)) {
            System.out.println("Booking restored.");
        }
    }

    private void promptDelete() {
        displayAll();
        int id = promptForInt("Enter booking ID to delete: ");
        System.out.print("Are you sure? This cannot be undone. (Y/N): ");
        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
            System.out.println("Delete cancelled.");
            return;
        }
        if (bookingService.delete(id)) {
            System.out.println("Booking deleted.");
        }
    }

    private void printTable(String header, List<TrainingBooking> list, String emptyMsg) {
        List<String[]> rows = new ArrayList<>();
        if (list != null) {
            for (TrainingBooking b : list) {
                String timeRange = b.getStartTime() != null && b.getEndTime() != null
                        ? b.getStartTime().toString().substring(0, 5) + "-" + b.getEndTime().toString().substring(0, 5)
                        : "-";
                BigDecimal hrs = b.getTotalHours() != null ? b.getTotalHours() : BigDecimal.ZERO;
                BigDecimal total = b.getTotalAmount() != null ? b.getTotalAmount() : BigDecimal.ZERO;
                rows.add(new String[]{
                        String.valueOf(b.getId()),
                        b.getMemberName(),
                        b.getInstructorName(),
                        b.getCategory(),
                        b.getBookingDate() != null ? b.getBookingDate().toString() : "-",
                        timeRange,
                        String.format("%.2f", hrs),
                        String.format("%.2f", total),
                        b.getStatus()
                });
            }
        }
        printFormattedTable(
                header,
                new String[]{"ID", "Member", "Instructor", "Category", "Date", "Time", "Hours", "Total", "Status"},
                new int[]{3, 16, 16, 20, 10, 13, 6, 8, 10},
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

    private Date promptForDate(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Date.valueOf(scanner.nextLine().trim());
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format (YYYY-MM-DD).");
            }
        }
    }

    private Time promptForTime(String msg) {
        while (true) {
            System.out.print(msg);
            String raw = scanner.nextLine().trim();
            if (raw.matches("\\d{2}:\\d{2}")) raw += ":00";
            try {
                return Time.valueOf(raw);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid time format (HH:MM or HH:MM:SS).");
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
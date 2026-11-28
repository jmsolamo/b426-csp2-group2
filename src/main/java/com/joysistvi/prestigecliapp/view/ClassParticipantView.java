package com.joysistvi.prestigecliapp.view;

import com.joysistvi.prestigecliapp.model.ClassParticipant;
import com.joysistvi.prestigecliapp.service.ClassParticipantService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClassParticipantView {

    private final ClassParticipantService cpService;
    private final Scanner scanner;

    public ClassParticipantView(ClassParticipantService cpService, Scanner scanner) {
        this.cpService = cpService;
        this.scanner = scanner;
    }

    public void handleMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n========================================");
            System.out.println("      CLASS PARTICIPANTS MANAGEMENT");
            System.out.println("========================================");
            System.out.println();
            System.out.println("  [1] View All Active Class Participants");
            System.out.println("  [2] Add Class Participant");
            System.out.println("  [3] Update Participant Status");
            System.out.println("  [4] View Participants by User ID");
            System.out.println("  [5] View Participants by Fitness Class ID");
            System.out.println("  [6] Archive Class Participant");
            System.out.println("  [7] Restore Class Participant");
            System.out.println("  [8] Delete Class Participant");
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
                case 5 -> promptFindByFitnessClassId();
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
        List<ClassParticipant> classParticipants = new ArrayList<>();

        for (ClassParticipant participant : cpService.findAll()) {
            if (!participant.isArchived()) {
                classParticipants.add(participant);
            }
        }

        printTable("----- Active Class Participants -----",
                classParticipants,
                "No class participants found.");
    }

    private void promptAdd() {
        int userId = promptForInt("Enter user ID: ");
        int fitnessClassId = promptForInt("Enter fitness class ID: ");

        System.out.print("Enter participant status: ");
        String status = scanner.nextLine();

        ClassParticipant classParticipant = new ClassParticipant(
                userId,
                fitnessClassId,
                status
        );

        if (cpService.add(classParticipant)) {
            System.out.println("Class participant added successfully!");
        } else {
            System.out.println("Failed to add class participant.");
        }
    }

    private void promptUpdateStatus() {
        displayAll();

        int id = promptForInt("Enter class participant ID: ");

        System.out.print("Enter new participant status: ");
        String status = scanner.nextLine();

        if (cpService.updateStatus(id, status)) {
            System.out.println("Participant status updated successfully!");
        } else {
            System.out.println("Failed to update participant status.");
        }
    }

    private void promptFindByUserId() {
        int userId = promptForInt("Enter user ID: ");

        printTable("----- Class Participants by User ID -----",
                cpService.findByUserId(userId),
                "No class participants found.");
    }

    private void promptFindByFitnessClassId() {
        int fitnessClassId = promptForInt("Enter fitness class ID: ");

        printTable("----- Class Participants by Fitness Class ID -----",
                cpService.findByFitnessClassId(fitnessClassId),
                "No class participants found.");
    }

    private void promptArchive() {
        displayAll();

        int id = promptForInt("Enter class participant ID to archive: ");

        System.out.print("Confirm archive ID " + id + "? (Y/N): ");

        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
            System.out.println("Archive cancelled.");
            return;
        }

        if (cpService.archive(id)) {
            System.out.println("Class participant archived.");
        } else {
            System.out.println("Failed to archive class participant.");
        }
    }

    private void promptRestore() {
        List<ClassParticipant> archived = new ArrayList<>();

        for (ClassParticipant participant : cpService.findAll()) {
            if (participant.isArchived()) {
                archived.add(participant);
            }
        }

        printTable("----- Archived Class Participants -----",
                archived,
                "No archived class participants found.");

        if (archived.isEmpty()) {
            return;
        }

        int id = promptForInt("Enter class participant ID to restore: ");

        if (cpService.restore(id)) {
            System.out.println("Class participant restored.");
        } else {
            System.out.println("Failed to restore class participant.");
        }
    }

    private void promptDelete() {
        displayAll();

        int id = promptForInt("Enter class participant ID to delete: ");

        System.out.print("Are you sure? This cannot be undone. (Y/N): ");

        if (!"Y".equalsIgnoreCase(scanner.nextLine().trim())) {
            System.out.println("Delete cancelled.");
            return;
        }

        if (cpService.remove(id)) {
            System.out.println("Class participant deleted.");
        } else {
            System.out.println("Failed to delete class participant.");
        }
    }

    private void printTable(String header, List<ClassParticipant> list, String emptyMsg) {
        System.out.println("\n" + header);

        if (list == null || list.isEmpty()) {
            System.out.println(emptyMsg);
            return;
        }

        System.out.printf("%-5s | %-10s | %-17s | %-22s | %-15s | %-10s%n",
                "ID", "User ID", "Fitness Class ID", "Enrollment Date", "Status", "Archived");

        System.out.println("-".repeat(100));

        for (ClassParticipant participant : list) {
            System.out.printf("%-5d | %-10d | %-17d | %-22s | %-15s | %-10s%n",
                    participant.getId(),
                    participant.getUserId(),
                    participant.getFitnessClassId(),
                    participant.getEnrollmentDate(),
                    participant.getStatus(),
                    participant.isArchived() ? "Yes" : "No");
        }

        System.out.println("-".repeat(100));
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
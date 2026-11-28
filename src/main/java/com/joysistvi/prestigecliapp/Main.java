package com.joysistvi.prestigecliapp;

import com.joysistvi.prestigecliapp.config.DbConnection;

import com.joysistvi.prestigecliapp.repository.ClassParticipantRepository;
import com.joysistvi.prestigecliapp.repository.ClassParticipantRepositoryImpl;
import com.joysistvi.prestigecliapp.repository.PurchaseHistoryRepository;
import com.joysistvi.prestigecliapp.repository.PurchaseHistoryRepositoryImpl;
import com.joysistvi.prestigecliapp.repository.SubscribedMemberRepository;
import com.joysistvi.prestigecliapp.repository.SubscribedMemberRepositoryImpl;

import com.joysistvi.prestigecliapp.service.ClassParticipantService;
import com.joysistvi.prestigecliapp.service.ClassParticipantServiceImpl;
import com.joysistvi.prestigecliapp.service.PurchaseHistoryService;
import com.joysistvi.prestigecliapp.service.PurchaseHistoryServiceImpl;
import com.joysistvi.prestigecliapp.service.SubscribedMemberService;
import com.joysistvi.prestigecliapp.service.SubscribedMemberServiceImpl;

import com.joysistvi.prestigecliapp.view.ClassParticipantView;
import com.joysistvi.prestigecliapp.view.PurchaseHistoryView;
import com.joysistvi.prestigecliapp.view.SubscribedMemberView;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        DbConnection dbConnection = new DbConnection();

        ClassParticipantRepository classParticipantRepository =
                new ClassParticipantRepositoryImpl(dbConnection);

        ClassParticipantService classParticipantService =
                new ClassParticipantServiceImpl(classParticipantRepository);

        SubscribedMemberRepository subscribedMemberRepository =
                new SubscribedMemberRepositoryImpl(dbConnection);

        SubscribedMemberService subscribedMemberService =
                new SubscribedMemberServiceImpl(subscribedMemberRepository);

        PurchaseHistoryRepository purchaseHistoryRepository =
                new PurchaseHistoryRepositoryImpl(dbConnection);

        PurchaseHistoryService purchaseHistoryService =
                new PurchaseHistoryServiceImpl(purchaseHistoryRepository);

        ClassParticipantView classParticipantView =
                new ClassParticipantView(classParticipantService, scanner);

        SubscribedMemberView subscribedMemberView =
                new SubscribedMemberView(subscribedMemberService, scanner);

        PurchaseHistoryView purchaseHistoryView =
                new PurchaseHistoryView(purchaseHistoryService, scanner);

        while (true) {
            System.out.println("\n===== PRESTIGE CLI APP =====");
            System.out.println("1. Class Participants");
            System.out.println("2. Subscribed Members");
            System.out.println("3. Purchase History");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> classParticipantView.handleMenu();
                case 2 -> subscribedMemberView.handleMenu();
                case 3 -> purchaseHistoryView.handleMenu();
                case 0 -> {
                    System.out.println("Exiting application...");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}
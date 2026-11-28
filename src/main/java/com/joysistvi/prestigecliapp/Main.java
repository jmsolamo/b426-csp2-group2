package com.joysistvi.prestigecliapp;

import com.joysistvi.prestigecliapp.config.DbConnection;
import com.joysistvi.prestigecliapp.model.PersonalTraining;
import com.joysistvi.prestigecliapp.repository.*;
import com.joysistvi.prestigecliapp.service.*;
import com.joysistvi.prestigecliapp.view.*;

import java.util.Scanner;

public class Main {

    private final Scanner scanner;
    private final UserView userView;


    public Main (Scanner scanner, UserView userView) {
        this.scanner = scanner;
        this.userView = userView;
    }

    public static void main(String[] args) {

        DbConnection dbConnection = new DbConnection();
        Scanner scanner = new Scanner(System.in);

        ClassParticipantRepository classParticipantRepository = new ClassParticipantRepositoryImpl(dbConnection);
        ClassParticipantService classParticipantService = new ClassParticipantServiceImpl(classParticipantRepository);
        ClassParticipantView classParticipantView = new ClassParticipantView(classParticipantService, scanner);

        PersonalTrainingRepository personalTrainingRepository = new PersonalTrainingRepositoryImpl(dbConnection);
        PersonalTrainingService personalTrainingService = new PersonalTrainingServiceImpl(personalTrainingRepository);
        PersonalTrainingView personalTrainingView = new PersonalTrainingView(personalTrainingService, scanner);

        PurchaseHistoryRepository purchaseHistoryRepository = new PurchaseHistoryRepositoryImpl(dbConnection);
        PurchaseHistoryService purchaseHistoryService = new PurchaseHistoryServiceImpl(purchaseHistoryRepository);
        PurchaseHistoryView purchaseHistoryView = new PurchaseHistoryView(purchaseHistoryService, scanner);

        SubscribedMemberRepository subscribedMemberRepository = new SubscribedMemberRepositoryImpl(dbConnection);
        SubscribedMemberService subscribedMemberService = new SubscribedMemberServiceImpl(subscribedMemberRepository);
        SubscribedMemberView subscribedMemberView = new SubscribedMemberView(subscribedMemberService, scanner);

        TopUpHistoryRepository topUpHistoryRepository = new TopUpHistoryRepositoryImpl(dbConnection);
        TopUpHistoryService topUpHistoryService = new TopUpHistoryServiceImpl(topUpHistoryRepository);
        TopUpHistoryView topUpHistoryView = new TopUpHistoryView(topUpHistoryService, scanner);

        TrainingBookingRepository trainingBookingRepository = new TrainingBookingRepositoryImpl(dbConnection);
        TrainingBookingService trainingBookingService = new TrainingBookingServiceImpl(trainingBookingRepository);
        TrainingBookingView trainingBookingView = new TrainingBookingView(trainingBookingService, personalTrainingView, scanner);

        UserRepository userRepository = new UserRepositoryImpl(dbConnection);
        UserService userService = new UserServiceImpl(userRepository);
        UserView userView = new UserView(userService, scanner);

    }
}

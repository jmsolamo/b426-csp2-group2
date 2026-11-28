package com.joysistvi.prestigecliapp;

import com.joysistvi.prestigecliapp.config.DbConnection;
import com.joysistvi.prestigecliapp.repository.UserRepository;
import com.joysistvi.prestigecliapp.repository.UserRepositoryImpl;
import com.joysistvi.prestigecliapp.service.UserService;
import com.joysistvi.prestigecliapp.service.UserServiceImpl;
import com.joysistvi.prestigecliapp.view.UserView;
import com.joysistvi.prestigecliapp.model.User;

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

        UserRepository userRepository =
                new UserRepositoryImpl(dbConnection);

        UserService userService =
                new UserServiceImpl(userRepository);

        UserView userView =
                new UserView(userService, scanner);

        User loggedInUser =
                userView.showLoginPrompt();

        if (loggedInUser == null) {
            System.out.println("Login Failed.");
            return;
        }

        if ("ADMIN".equalsIgnoreCase(loggedInUser.getRole())) {
            userView.handleAdminUserMenu();
        }
    }
}

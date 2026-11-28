package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.User;
import com.joysistvi.prestigecliapp.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ── Authentication ──────────────────────────────────────

    @Override
    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Validation Error: Username cannot be blank.");
            return null;
        }
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Validation Error: Password cannot be blank.");
            return null;
        }

        User user = userRepository.findByUsername(username.trim());
        if (user == null) {
            System.out.println("Login failed: Invalid username or password.");
            return null;
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            System.out.println("Login failed: Invalid username or password.");
            return null;
        }

        return user;
    }

    @Override
    public boolean registerMember(String firstName, String lastName, String username,
                                  String email, String password) {
        if (!isValidRegistrationInput(firstName, lastName, username, email, password)) {
            return false;
        }

        if (userRepository.findByUsername(username.trim()) != null) {
            System.out.println("Registration failed: Username '" + username.trim() + "' is already taken.");
            return false;
        }

        if (userRepository.findByEmail(email.trim()) != null) {
            System.out.println("Registration failed: Email '" + email.trim() + "' is already registered.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User newUser = new User(firstName.trim(), lastName.trim(), username.trim(),
                email.trim(), hashedPassword, "MEMBER");

        return userRepository.save(newUser);
    }

    // ── Admin: Create instructor ────────────────────────────

    @Override
    public boolean createInstructor(String firstName, String lastName, String username,
                                    String email, String password) {
        if (!isValidRegistrationInput(firstName, lastName, username, email, password)) {
            return false;
        }

        if (userRepository.findByUsername(username.trim()) != null) {
            System.out.println("Creation failed: Username '" + username.trim() + "' is already taken.");
            return false;
        }

        if (userRepository.findByEmail(email.trim()) != null) {
            System.out.println("Creation failed: Email '" + email.trim() + "' is already registered.");
            return false;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        User instructor = new User(firstName.trim(), lastName.trim(), username.trim(),
                email.trim(), hashedPassword, "INSTRUCTOR");

        return userRepository.save(instructor);
    }

    // ── Read ────────────────────────────────────────────────

    @Override
    public User findById(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: User ID must be positive.");
            return null;
        }
        return userRepository.findById(id);
    }

    @Override
    public List<User> getAllActiveUsers() {
        return userRepository.findAllActive();
    }

    @Override
    public List<User> getAllArchivedUsers() {
        return userRepository.findAllArchived();
    }

    @Override
    public List<User> getUsersByRole(String role) {
        if (role == null || role.trim().isEmpty()) {
            System.out.println("Validation Error: Role cannot be blank.");
            return new ArrayList<>();
        }
        return userRepository.findByRole(role.trim().toUpperCase());
    }

    @Override
    public List<User> searchUsers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Validation Error: Search keyword cannot be blank.");
            return new ArrayList<>();
        }
        return userRepository.search(keyword.trim());
    }

    // ── Update ──────────────────────────────────────────────

    @Override
    public boolean updateUser(User user) {
        if (user == null || user.getId() <= 0) {
            System.out.println("Validation Error: Invalid user.");
            return false;
        }
        return userRepository.update(user);
    }

    @Override
    public boolean changePassword(int userId, String currentPassword, String newPassword) {
        if (userId <= 0) {
            System.out.println("Validation Error: User ID must be positive.");
            return false;
        }
        if (currentPassword == null || currentPassword.isEmpty()) {
            System.out.println("Validation Error: Current password cannot be blank.");
            return false;
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            System.out.println("Validation Error: New password cannot be blank.");
            return false;
        }
        if (newPassword.trim().length() < 6) {
            System.out.println("Validation Error: New password must be at least 6 characters.");
            return false;
        }

        User user = userRepository.findById(userId);
        if (user == null) {
            System.out.println("Error: User not found.");
            return false;
        }

        if (!BCrypt.checkpw(currentPassword, user.getPassword())) {
            System.out.println("Error: Current password is incorrect.");
            return false;
        }

        String hashedNew = BCrypt.hashpw(newPassword.trim(), BCrypt.gensalt());
        return userRepository.updatePassword(userId, hashedNew);
    }

    // ── Archive / Restore / Delete ──────────────────────────

    @Override
    public boolean archiveUser(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: User ID must be positive.");
            return false;
        }
        return userRepository.archiveById(id);
    }

    @Override
    public boolean restoreUser(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: User ID must be positive.");
            return false;
        }
        return userRepository.restoreById(id);
    }

    @Override
    public boolean deleteUser(int id) {
        if (id <= 0) {
            System.out.println("Validation Error: User ID must be positive.");
            return false;
        }
        return userRepository.deleteById(id);
    }

    // ── Private validation helpers ──────────────────────────

    private boolean isValidRegistrationInput(String firstName, String lastName,
                                             String username, String email, String password) {
        if (firstName == null || firstName.trim().isEmpty()) {
            System.out.println("Validation Error: First name cannot be blank.");
            return false;
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            System.out.println("Validation Error: Last name cannot be blank.");
            return false;
        }
        if (username == null || username.trim().isEmpty()) {
            System.out.println("Validation Error: Username cannot be blank.");
            return false;
        }
        if (email == null || email.trim().isEmpty()) {
            System.out.println("Validation Error: Email cannot be blank.");
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            System.out.println("Validation Error: Password cannot be blank.");
            return false;
        }
        if (password.trim().length() < 6) {
            System.out.println("Validation Error: Password must be at least 6 characters.");
            return false;
        }
        return true;
    }
}

package com.joysistvi.prestigecliapp.service;

import com.joysistvi.prestigecliapp.model.User;

import java.util.List;

public interface UserService {

    // ── Authentication ──────────────────────────────────────
    User login(String username, String password);

    boolean registerMember(String firstName, String lastName, String username,
                           String email, String password);

    // ── Admin: Create instructor ────────────────────────────
    boolean createInstructor(String firstName, String lastName, String username,
                             String email, String password);

    // ── Read ────────────────────────────────────────────────
    User findById(int id);

    List<User> getAllActiveUsers();

    List<User> getAllArchivedUsers();

    List<User> getUsersByRole(String role);

    List<User> searchUsers(String keyword);

    // ── Update ──────────────────────────────────────────────
    boolean updateUser(User user);

    boolean changePassword(int userId, String currentPassword, String newPassword);

    // ── Archive / Restore / Delete ──────────────────────────
    boolean archiveUser(int id);

    boolean restoreUser(int id);

    boolean deleteUser(int id);
}

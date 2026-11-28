package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.model.User;

import java.util.List;

public interface UserRepository {

    boolean save(User user);

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(int id);

    List<User> findAllActive();

    List<User> findAllArchived();

    List<User> findByRole(String role);

    List<User> search(String keyword);

    boolean update(User user);

    boolean updatePassword(int userId, String hashedPassword);

    boolean archiveById(int id);

    boolean restoreById(int id);

    boolean deleteById(int id);
}

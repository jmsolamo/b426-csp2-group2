package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.config.DbConnection;
import com.joysistvi.prestigecliapp.model.ClassParticipant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassParticipantRepositoryImpl implements ClassParticipantRepository {

    private final DbConnection dbConnection;

    public ClassParticipantRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<ClassParticipant> findAll() {
        List<ClassParticipant> classParticipants = new ArrayList<>();
        String sql = "SELECT * FROM class_participants";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                classParticipants.add(new ClassParticipant(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("fitness_class_id"),
                        resultSet.getTimestamp("enrollment_date"),
                        resultSet.getString("status"),
                        resultSet.getBoolean("is_archived"),
                        resultSet.getTimestamp("created_at")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Search failed" + e.getMessage());
        }

        return classParticipants;
    }

    @Override
    public ClassParticipant findById(int id) {
        String sql = "SELECT * FROM class_participants WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new ClassParticipant(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("fitness_class_id"),
                            resultSet.getTimestamp("enrollment_date"),
                            resultSet.getString("status"),
                            resultSet.getBoolean("is_archived"),
                            resultSet.getTimestamp("created_at")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed" + e.getMessage());
        }

        return null;
    }

    @Override
    public List<ClassParticipant> findByUserId(int userId) {
        List<ClassParticipant> classParticipants = new ArrayList<>();
        String sql = "SELECT * FROM class_participants WHERE user_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    classParticipants.add(new ClassParticipant(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("fitness_class_id"),
                            resultSet.getTimestamp("enrollment_date"),
                            resultSet.getString("status"),
                            resultSet.getBoolean("is_archived"),
                            resultSet.getTimestamp("created_at")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed" + e.getMessage());
        }

        return classParticipants;
    }

    @Override
    public List<ClassParticipant> findByFitnessClassId(int fitnessClassId) {
        List<ClassParticipant> classParticipants = new ArrayList<>();
        String sql = "SELECT * FROM class_participants WHERE fitness_class_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, fitnessClassId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    classParticipants.add(new ClassParticipant(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("fitness_class_id"),
                            resultSet.getTimestamp("enrollment_date"),
                            resultSet.getString("status"),
                            resultSet.getBoolean("is_archived"),
                            resultSet.getTimestamp("created_at")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed" + e.getMessage());
        }

        return classParticipants;
    }

    @Override
    public boolean add(ClassParticipant classParticipant) {
        String sql = "INSERT INTO class_participants (user_id, fitness_class_id, enrollment_date, status, is_archived, created_at) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, classParticipant.getUserId());
            preparedStatement.setInt(2, classParticipant.getFitnessClassId());
            preparedStatement.setTimestamp(3, classParticipant.getEnrollmentDate());
            preparedStatement.setString(4, classParticipant.getStatus());
            preparedStatement.setBoolean(5, classParticipant.isArchived());
            preparedStatement.setTimestamp(6, classParticipant.getCreatedAt());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Adding failed" + e.getMessage());
            return false;
        }
    }


    @Override
    public boolean remove(int id) {
        String sql = "DELETE FROM class_participants WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Removing failed" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archive(int id) {
        String sql = "UPDATE class_participants SET is_archived = 1 WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Archiving failed" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restore(int id) {
        String sql = "UPDATE class_participants SET is_archived = 0 WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Restoring failed" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE class_participants SET status = ? WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Updating failed" + e.getMessage());
            return false;
        }
    }
}
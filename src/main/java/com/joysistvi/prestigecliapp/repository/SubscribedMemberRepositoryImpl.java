package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.config.DbConnection;
import com.joysistvi.prestigecliapp.model.SubscribedMember;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscribedMemberRepositoryImpl implements SubscribedMemberRepository {

    private final DbConnection dbConnection;

    public SubscribedMemberRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<SubscribedMember> findAll() {

        List<SubscribedMember> subscribedMembers = new ArrayList<>();

        String sql = "SELECT * FROM subscribed_members";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                subscribedMembers.add(new SubscribedMember(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("membership_plan_id"),
                        resultSet.getDate("start_date"),
                        resultSet.getDate("expiration_date"),
                        resultSet.getString("status"),
                        resultSet.getBoolean("is_archived"),
                        resultSet.getTimestamp("created_at")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Finding all subscribed members failed" + e.getMessage());
        }

        return subscribedMembers;
    }

    @Override
    public SubscribedMember findById(int id) {

        String sql = "SELECT * FROM subscribed_members WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {

                    return new SubscribedMember(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("membership_plan_id"),
                            resultSet.getDate("start_date"),
                            resultSet.getDate("expiration_date"),
                            resultSet.getString("status"),
                            resultSet.getBoolean("is_archived"),
                            resultSet.getTimestamp("created_at")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Finding subscribed member failed" + e.getMessage());
        }

        return null;
    }

    @Override
    public List<SubscribedMember> findByUserId(int userId) {

        List<SubscribedMember> subscribedMembers = new ArrayList<>();

        String sql = "SELECT * FROM subscribed_members WHERE user_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    subscribedMembers.add(new SubscribedMember(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("membership_plan_id"),
                            resultSet.getDate("start_date"),
                            resultSet.getDate("expiration_date"),
                            resultSet.getString("status"),
                            resultSet.getBoolean("is_archived"),
                            resultSet.getTimestamp("created_at")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Finding subscribed members by user ID failed" + e.getMessage());
        }

        return subscribedMembers;
    }

    @Override
    public List<SubscribedMember> findByMembershipPlanId(int membershipPlanId) {

        List<SubscribedMember> subscribedMembers = new ArrayList<>();

        String sql = "SELECT * FROM subscribed_members WHERE membership_plan_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, membershipPlanId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    subscribedMembers.add(new SubscribedMember(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("membership_plan_id"),
                            resultSet.getDate("start_date"),
                            resultSet.getDate("expiration_date"),
                            resultSet.getString("status"),
                            resultSet.getBoolean("is_archived"),
                            resultSet.getTimestamp("created_at")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Finding subscribed members by membership plan ID failed" + e.getMessage());
        }

        return subscribedMembers;
    }

    @Override
    public boolean add(SubscribedMember subscribedMember) {

        String sql = "INSERT INTO subscribed_members (user_id, membership_plan_id, start_date, expiration_date, status, is_archived, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, subscribedMember.getUserId());
            preparedStatement.setInt(2, subscribedMember.getMembershipPlanId());
            preparedStatement.setDate(3, subscribedMember.getStartDate());
            preparedStatement.setDate(4, subscribedMember.getExpirationDate());
            preparedStatement.setString(5, subscribedMember.getStatus());
            preparedStatement.setBoolean(6, subscribedMember.isArchived());
            preparedStatement.setTimestamp(7, subscribedMember.getCreatedAt());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Adding subscribed member failed" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean save(SubscribedMember subscribedMember) {

        String sql = "INSERT INTO subscribed_members (user_id, membership_plan_id, start_date, expiration_date, status, is_archived, created_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, subscribedMember.getUserId());
            preparedStatement.setInt(2, subscribedMember.getMembershipPlanId());
            preparedStatement.setDate(3, subscribedMember.getStartDate());
            preparedStatement.setDate(4, subscribedMember.getExpirationDate());
            preparedStatement.setString(5, subscribedMember.getStatus());
            preparedStatement.setBoolean(6, subscribedMember.isArchived());
            preparedStatement.setTimestamp(7, subscribedMember.getCreatedAt());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Saving subscribed member failed" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean remove(int id) {

        String sql = "DELETE FROM subscribed_members WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Removing subscribed member failed" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archive(int id) {

        String sql = "UPDATE subscribed_members SET is_archived = 1 WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Archiving subscribed member failed" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restore(int id) {

        String sql = "UPDATE subscribed_members SET is_archived = 0 WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Restoring subscribed member failed" + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStatus(int id, String status) {

        String sql = "UPDATE subscribed_members SET status = ? WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Updating subscribed member status failed" + e.getMessage());
            return false;
        }
    }

}
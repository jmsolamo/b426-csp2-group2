package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.config.DbConnection;
import com.joysistvi.prestigecliapp.model.PurchaseHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseHistoryRepositoryImpl implements PurchaseHistoryRepository {

    private final DbConnection dbConnection;

    public PurchaseHistoryRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<PurchaseHistory> findAll() {
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
        String sql = "SELECT * FROM purchase_history";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                purchaseHistories.add(new PurchaseHistory(
                        resultSet.getInt("id"),
                        resultSet.getInt("membership_card_id"),
                        resultSet.getString("purchase_reference"),
                        resultSet.getString("purchase_type"),
                        resultSet.getInt("item_id"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("status"),
                        resultSet.getTimestamp("purchase_date")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Search failed " + e.getMessage());
        }

        return purchaseHistories;
    }

    @Override
    public PurchaseHistory findById(int id) {
        String sql = "SELECT * FROM purchase_history WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new PurchaseHistory(
                            resultSet.getInt("id"),
                            resultSet.getInt("membership_card_id"),
                            resultSet.getString("purchase_reference"),
                            resultSet.getString("purchase_type"),
                            resultSet.getInt("item_id"),
                            resultSet.getDouble("amount"),
                            resultSet.getString("status"),
                            resultSet.getTimestamp("purchase_date")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<PurchaseHistory> findByMembershipCardId(int membershipCardId) {
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
        String sql = "SELECT * FROM purchase_history WHERE membership_card_id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, membershipCardId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    purchaseHistories.add(new PurchaseHistory(
                            resultSet.getInt("id"),
                            resultSet.getInt("membership_card_id"),
                            resultSet.getString("purchase_reference"),
                            resultSet.getString("purchase_type"),
                            resultSet.getInt("item_id"),
                            resultSet.getDouble("amount"),
                            resultSet.getString("status"),
                            resultSet.getTimestamp("purchase_date")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed " + e.getMessage());
        }

        return purchaseHistories;
    }

    @Override
    public PurchaseHistory findByPurchaseReference(String purchaseReference) {
        String sql = "SELECT * FROM purchase_history WHERE purchase_reference = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, purchaseReference);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new PurchaseHistory(
                            resultSet.getInt("id"),
                            resultSet.getInt("membership_card_id"),
                            resultSet.getString("purchase_reference"),
                            resultSet.getString("purchase_type"),
                            resultSet.getInt("item_id"),
                            resultSet.getDouble("amount"),
                            resultSet.getString("status"),
                            resultSet.getTimestamp("purchase_date")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<PurchaseHistory> findByPurchaseType(String purchaseType) {
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();
        String sql = "SELECT * FROM purchase_history WHERE purchase_type = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, purchaseType);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    purchaseHistories.add(new PurchaseHistory(
                            resultSet.getInt("id"),
                            resultSet.getInt("membership_card_id"),
                            resultSet.getString("purchase_reference"),
                            resultSet.getString("purchase_type"),
                            resultSet.getInt("item_id"),
                            resultSet.getDouble("amount"),
                            resultSet.getString("status"),
                            resultSet.getTimestamp("purchase_date")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed " + e.getMessage());
        }

        return purchaseHistories;
    }

    @Override
    public List<PurchaseHistory> search(String keyword) {
        List<PurchaseHistory> purchaseHistories = new ArrayList<>();

        String cleanKeyword = keyword.trim();

        String sql = "SELECT * FROM purchase_history" +
                " WHERE purchase_reference LIKE ? OR purchase_type LIKE ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + cleanKeyword + "%");
            preparedStatement.setString(2, "%" + cleanKeyword + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    purchaseHistories.add(new PurchaseHistory(
                            resultSet.getInt("id"),
                            resultSet.getInt("membership_card_id"),
                            resultSet.getString("purchase_reference"),
                            resultSet.getString("purchase_type"),
                            resultSet.getInt("item_id"),
                            resultSet.getDouble("amount"),
                            resultSet.getString("status"),
                            resultSet.getTimestamp("purchase_date")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Search failed " + e.getMessage());
        }

        return purchaseHistories;
    }

    @Override
    public boolean save(PurchaseHistory purchaseHistory) {
        String sql = "INSERT INTO purchase_history (membership_card_id, purchase_reference, purchase_type, item_id, amount, status, purchase_date) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, purchaseHistory.getMembershipCardId());
            preparedStatement.setString(2, purchaseHistory.getPurchaseReference());
            preparedStatement.setString(3, purchaseHistory.getPurchaseType());
            preparedStatement.setInt(4, purchaseHistory.getItemId());
            preparedStatement.setDouble(5, purchaseHistory.getAmount());
            preparedStatement.setString(6, purchaseHistory.getStatus());
            preparedStatement.setTimestamp(7, purchaseHistory.getPurchaseDate());

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Saving failed " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE purchase_history SET status = ? WHERE id = ?";

        try (Connection connection = dbConnection.connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Updating failed " + e.getMessage());
            return false;
        }
    }
}
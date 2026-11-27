package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.config.DbConnection;
import com.joysistvi.prestigecliapp.model.TopUpHistory;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TopUpHistoryRepositoryImpl implements TopUpHistoryRepository {
    private final DbConnection dbConnection;

    private static final String BASE_SELECT =
            "SELECT th.id, th.membership_card_id, th.topup_reference, th.payment_reference, " +
                    "th.amount, th.payment_method, th.status, th.topup_date, " +
                    "mc.user_id, mc.card_no, CONCAT(u.first_name, ' ', u.last_name) AS member_name " +
                    "FROM topup_history th " +
                    "JOIN membership_card mc ON th.membership_card_id = mc.id " +
                    "JOIN users u ON mc.user_id = u.id ";

    public TopUpHistoryRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<TopUpHistory> findAll() {
        List<TopUpHistory> list = new ArrayList<>();
        String query = BASE_SELECT + "ORDER BY th.id DESC";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findAll): " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<TopUpHistory> findByCardId(int membershipCardId) {
        List<TopUpHistory> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE th.membership_card_id = ? ORDER BY th.id DESC";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, membershipCardId);
            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findByCardId): " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<TopUpHistory> findByUserId(int userId) {
        List<TopUpHistory> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE mc.user_id = ? ORDER BY th.id DESC";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, userId);
            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findByUserId): " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<TopUpHistory> findByStatus(String status) {
        List<TopUpHistory> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE th.status = ? ORDER BY th.id DESC";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, status);
            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findByStatus): " + e.getMessage());
        }
        return list;
    }

    @Override
    public TopUpHistory findById(int id) {
        String query = BASE_SELECT + "WHERE th.id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findById): " + e.getMessage());
        }
        return null;
    }

    // Direct lookup so TopUpHistory works before MembershipCardRepository is implemented
    @Override
    public int findActiveCardIdByUserId(int userId) {
        String query = "SELECT id FROM membership_card WHERE user_id = ? AND status = 'ACTIVE' AND is_archived = 0 ORDER BY id ASC LIMIT 1";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, userId);
            try (ResultSet rs = prep.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findActiveCardIdByUserId): " + e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean save(TopUpHistory topup) {
        return saveAndReturnId(topup) > 0;
    }

    @Override
    public int saveAndReturnId(TopUpHistory topup) {
        String insertSql = "INSERT INTO topup_history (membership_card_id, topup_reference, payment_reference, amount, payment_method, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        String updateRefSql = "UPDATE topup_history SET topup_reference = ? WHERE id = ?";
        String initialRef = (topup.getTopupReference() == null || topup.getTopupReference().isBlank())
                ? "TEMP-" + UUID.randomUUID().toString().substring(0, 12).toUpperCase() : topup.getTopupReference();

        try (Connection conn = dbConnection.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement prep = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                prep.setInt(1, topup.getMembershipCardId());
                prep.setString(2, initialRef);
                prep.setString(3, topup.getPaymentReference());
                prep.setBigDecimal(4, topup.getAmount());
                prep.setString(5, topup.getPaymentMethod());
                prep.setString(6, topup.getStatus());

                int affected = prep.executeUpdate();
                if (affected > 0) {
                    try (ResultSet keys = prep.getGeneratedKeys()) {
                        if (keys.next()) {
                            int id = keys.getInt(1);
                            topup.setId(id);
                            if (initialRef.startsWith("TEMP-")) {
                                String datedRef = String.format("TOP%s%05d",
                                        LocalDate.now().format(DateTimeFormatter.ofPattern("MMddyyyy")), id);
                                try (PreparedStatement upd = conn.prepareStatement(updateRefSql)) {
                                    upd.setString(1, datedRef);
                                    upd.setInt(2, id);
                                    upd.executeUpdate();
                                }
                                topup.setTopupReference(datedRef);
                            }
                            conn.commit();
                            return id;
                        }
                    }
                }
                conn.rollback();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (save): " + e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean updateStatus(int id, String status) {
        String query = "UPDATE topup_history SET status = ? WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setString(1, status);
            prep.setInt(2, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (updateStatus): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean completeTopupAtomically(int topupId, int cardId, BigDecimal amount) {
        String updateTopup = "UPDATE topup_history SET status = 'COMPLETED' WHERE id = ? AND status = 'PENDING'";
        String creditCard = "UPDATE membership_card SET balance = balance + ? WHERE id = ?";
        try (Connection conn = dbConnection.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement p1 = conn.prepareStatement(updateTopup);
                 PreparedStatement p2 = conn.prepareStatement(creditCard)) {
                p1.setInt(1, topupId);
                if (p1.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
                p2.setBigDecimal(1, amount);
                p2.setInt(2, cardId);
                if (p2.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (completeTopupAtomically): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean refundTopupAtomically(int topupId, int cardId, BigDecimal amount) {
        String updateTopup = "UPDATE topup_history SET status = 'REFUNDED' WHERE id = ? AND status = 'COMPLETED'";
        String debitCard = "UPDATE membership_card SET balance = balance - ? WHERE id = ? AND balance >= ?";
        try (Connection conn = dbConnection.connect()) {
            conn.setAutoCommit(false);
            try (PreparedStatement p1 = conn.prepareStatement(updateTopup);
                 PreparedStatement p2 = conn.prepareStatement(debitCard)) {
                p1.setInt(1, topupId);
                if (p1.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
                p2.setBigDecimal(1, amount);
                p2.setInt(2, cardId);
                p2.setBigDecimal(3, amount);
                if (p2.executeUpdate() == 0) {
                    conn.rollback();
                    return false;
                }
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (refundTopupAtomically): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        String query = "DELETE FROM topup_history WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (deleteById): " + e.getMessage());
            return false;
        }
    }

    private TopUpHistory mapRow(ResultSet rs) throws SQLException {
        TopUpHistory th = new TopUpHistory();
        th.setId(rs.getInt("id"));
        th.setMembershipCardId(rs.getInt("membership_card_id"));
        th.setTopupReference(rs.getString("topup_reference"));
        th.setPaymentReference(rs.getString("payment_reference"));
        th.setAmount(rs.getBigDecimal("amount"));
        th.setPaymentMethod(rs.getString("payment_method"));
        th.setStatus(rs.getString("status"));
        th.setTopupDate(rs.getTimestamp("topup_date"));
        th.setUserId(rs.getInt("user_id"));
        th.setCardNo(rs.getString("card_no"));
        th.setMemberName(rs.getString("member_name"));
        return th;
    }
}
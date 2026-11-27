package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.config.DbConnection;
import com.joysistvi.prestigecliapp.model.TrainingBooking;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TrainingBookingRepositoryImpl implements TrainingBookingRepository {
    private final DbConnection dbConnection;

    private static final String BASE_SELECT =
            "SELECT tb.id, tb.user_id, tb.personal_training_id, tb.booking_date, tb.start_time, tb.end_time, " +
                    "tb.total_hours, tb.total_amount, tb.status, tb.is_archived, tb.created_at, " +
                    "CONCAT(m.first_name, ' ', m.last_name) AS member_name, m.username AS member_username, " +
                    "pt.instructor_id, CONCAT(inst.first_name, ' ', inst.last_name) AS instructor_name, " +
                    "pt.category, pt.hourly_rate " +
                    "FROM training_booking tb " +
                    "JOIN users m ON tb.user_id = m.id " +
                    "JOIN personal_training pt ON tb.personal_training_id = pt.id " +
                    "JOIN users inst ON pt.instructor_id = inst.id ";

    public TrainingBookingRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<TrainingBooking> findAllActive() {
        List<TrainingBooking> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE tb.is_archived = 0 ORDER BY tb.booking_date ASC, tb.start_time ASC, tb.id ASC";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findAllActive): " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<TrainingBooking> findAllArchived() {
        List<TrainingBooking> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE tb.is_archived = 1 ORDER BY tb.id ASC";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query);
             ResultSet rs = prep.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findAllArchived): " + e.getMessage());
        }
        return list;
    }

    @Override
    public List<TrainingBooking> findByUserId(int userId) {
        List<TrainingBooking> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE tb.user_id = ? AND tb.is_archived = 0 ORDER BY tb.id DESC";
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
    public List<TrainingBooking> findByInstructorId(int instructorId) {
        List<TrainingBooking> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE pt.instructor_id = ? AND tb.is_archived = 0 ORDER BY tb.booking_date ASC, tb.start_time ASC";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, instructorId);
            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (findByInstructorId): " + e.getMessage());
        }
        return list;
    }

    @Override
    public TrainingBooking findById(int id) {
        String query = BASE_SELECT + "WHERE tb.id = ?";
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

    @Override
    public boolean save(TrainingBooking b) {
        return saveAndReturnId(b) > 0;
    }

    @Override
    public int saveAndReturnId(TrainingBooking b) {
        String query = "INSERT INTO training_booking (user_id, personal_training_id, booking_date, start_time, end_time, total_hours, total_amount, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prep.setInt(1, b.getUserId());
            prep.setInt(2, b.getPersonalTrainingId());
            prep.setDate(3, b.getBookingDate());
            prep.setTime(4, b.getStartTime());
            prep.setTime(5, b.getEndTime());
            prep.setBigDecimal(6, b.getTotalHours());
            prep.setBigDecimal(7, b.getTotalAmount());
            prep.setString(8, b.getStatus());
            int affected = prep.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = prep.getGeneratedKeys()) {
                    if (keys.next()) {
                        int id = keys.getInt(1);
                        b.setId(id);
                        return id;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (save): " + e.getMessage());
        }
        return -1;
    }

    @Override
    public boolean updateStatus(int id, String status) {
        String query = "UPDATE training_booking SET status = ? WHERE id = ?";
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
    public boolean archiveById(int id) {
        String query = "UPDATE training_booking SET is_archived = 1 WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (archiveById): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean restoreById(int id) {
        String query = "UPDATE training_booking SET is_archived = 0 WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (restoreById): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deleteById(int id) {
        String query = "DELETE FROM training_booking WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (deleteById): " + e.getMessage());
            return false;
        }
    }

    private TrainingBooking mapRow(ResultSet rs) throws SQLException {
        TrainingBooking tb = new TrainingBooking();
        tb.setId(rs.getInt("id"));
        tb.setUserId(rs.getInt("user_id"));
        tb.setPersonalTrainingId(rs.getInt("personal_training_id"));
        tb.setBookingDate(rs.getDate("booking_date"));
        tb.setStartTime(rs.getTime("start_time"));
        tb.setEndTime(rs.getTime("end_time"));
        tb.setTotalHours(rs.getBigDecimal("total_hours"));
        tb.setTotalAmount(rs.getBigDecimal("total_amount"));
        tb.setStatus(rs.getString("status"));
        tb.setArchived(rs.getInt("is_archived") == 1);
        tb.setCreatedAt(rs.getTimestamp("created_at"));
        tb.setMemberName(rs.getString("member_name"));
        tb.setMemberUsername(rs.getString("member_username"));
        tb.setInstructorId(rs.getInt("instructor_id"));
        tb.setInstructorName(rs.getString("instructor_name"));
        tb.setCategory(rs.getString("category"));
        tb.setHourlyRate(rs.getBigDecimal("hourly_rate"));
        return tb;
    }
}
package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.config.DbConnection;
import com.joysistvi.prestigecliapp.model.PersonalTraining;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PersonalTrainingRepositoryImpl implements PersonalTrainingRepository {
    private final DbConnection dbConnection;

    private static final String BASE_SELECT =
            "SELECT pt.id, pt.instructor_id, pt.category, pt.description, pt.hourly_rate, pt.is_archived, pt.created_at, " +
                    "CONCAT(u.first_name, ' ', u.last_name) AS instructor_name " +
                    "FROM personal_training pt " +
                    "JOIN users u ON pt.instructor_id = u.id ";

    public PersonalTrainingRepositoryImpl(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<PersonalTraining> findAllActive() {
        List<PersonalTraining> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE pt.is_archived = 0 ORDER BY pt.id ASC";
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
    public List<PersonalTraining> findAllArchived() {
        List<PersonalTraining> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE pt.is_archived = 1 ORDER BY pt.id ASC";
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
    public List<PersonalTraining> findByInstructorId(int instructorId) {
        List<PersonalTraining> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE pt.instructor_id = ? AND pt.is_archived = 0 ORDER BY pt.id ASC";
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
    public PersonalTraining findById(int id) {
        String query = BASE_SELECT + "WHERE pt.id = ?";
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
    public boolean save(PersonalTraining pt) {
        return saveAndReturnId(pt) > 0;
    }

    @Override
    public int saveAndReturnId(PersonalTraining pt) {
        String query = "INSERT INTO personal_training (instructor_id, category, description, hourly_rate) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prep.setInt(1, pt.getInstructorId());
            prep.setString(2, pt.getCategory());
            prep.setString(3, pt.getDescription());
            prep.setBigDecimal(4, pt.getHourlyRate());
            int affected = prep.executeUpdate();
            if (affected > 0) {
                try (ResultSet keys = prep.getGeneratedKeys()) {
                    if (keys.next()) {
                        int id = keys.getInt(1);
                        pt.setId(id);
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
    public boolean update(PersonalTraining pt) {
        String query = "UPDATE personal_training SET instructor_id = ?, category = ?, description = ?, hourly_rate = ? WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, pt.getInstructorId());
            prep.setString(2, pt.getCategory());
            prep.setString(3, pt.getDescription());
            prep.setBigDecimal(4, pt.getHourlyRate());
            prep.setInt(5, pt.getId());
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (update): " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean archiveById(int id) {
        String query = "UPDATE personal_training SET is_archived = 1 WHERE id = ?";
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
        String query = "UPDATE personal_training SET is_archived = 0 WHERE id = ?";
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
        String query = "DELETE FROM personal_training WHERE id = ?";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            prep.setInt(1, id);
            return prep.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Repository Error (deleteById): " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<PersonalTraining> searchByKeyword(String keyword) {
        List<PersonalTraining> list = new ArrayList<>();
        String query = BASE_SELECT + "WHERE pt.is_archived = 0 AND (pt.category LIKE ? OR pt.description LIKE ?) ORDER BY pt.id ASC";
        try (Connection conn = dbConnection.connect();
             PreparedStatement prep = conn.prepareStatement(query)) {
            String pattern = "%" + keyword + "%";
            prep.setString(1, pattern);
            prep.setString(2, pattern);
            try (ResultSet rs = prep.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Repository Error (searchByKeyword): " + e.getMessage());
        }
        return list;
    }

    private PersonalTraining mapRow(ResultSet rs) throws SQLException {
        PersonalTraining pt = new PersonalTraining();
        pt.setId(rs.getInt("id"));
        pt.setInstructorId(rs.getInt("instructor_id"));
        pt.setCategory(rs.getString("category"));
        pt.setDescription(rs.getString("description"));
        pt.setHourlyRate(rs.getBigDecimal("hourly_rate"));
        pt.setArchived(rs.getInt("is_archived") == 1);
        pt.setCreatedAt(rs.getTimestamp("created_at"));
        pt.setInstructorName(rs.getString("instructor_name"));
        return pt;
    }
}
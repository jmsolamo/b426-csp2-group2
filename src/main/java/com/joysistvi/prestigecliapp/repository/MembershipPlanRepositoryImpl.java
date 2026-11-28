package com.joysistvi.prestigecliapp.repository;

import com.joysistvi.prestigecliapp.config.DbConnection;
import com.joysistvi.prestigecliapp.config.DbConnection;
import com.joysistvi.prestigecliapp.model.MembershipPlan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MembershipPlanRepositoryImpl implements MembershipPlanRepository {

    @Override
    public List<MembershipPlan> findAll() {
        List<MembershipPlan> plans = new ArrayList<>();

        String sql = "SELECT * FROM membership_plans WHERE is_archived = 0";

        try (
                Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {
                MembershipPlan plan = new MembershipPlan();

                plan.setId(rs.getInt("id"));
                plan.setName(rs.getString("name"));
                plan.setDescription(rs.getString("description"));
                plan.setPrice(rs.getDouble("price"));
                plan.setDurationDays(rs.getInt("duration_days"));
                plan.setInclusions(rs.getString("inclusions"));

                if (rs.getObject("class_limit") != null) {
                    plan.setClassLimit(rs.getInt("class_limit"));
                }

                plans.add(plan);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return plans;
    }

    @Override
    public MembershipPlan findById(int id) {

        String sql = "SELECT * FROM membership_plans WHERE id = ?";

        try (
                Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                MembershipPlan plan = new MembershipPlan();

                plan.setId(rs.getInt("id"));
                plan.setName(rs.getString("name"));
                plan.setDescription(rs.getString("description"));
                plan.setPrice(rs.getDouble("price"));
                plan.setDurationDays(rs.getInt("duration_days"));
                plan.setInclusions(rs.getString("inclusions"));

                if (rs.getObject("class_limit") != null) {
                    plan.setClassLimit(rs.getInt("class_limit"));
                }

                return plan;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean save(MembershipPlan plan) {

        String sql = """
                INSERT INTO membership_plans
                (name, description, price, duration_days, inclusions, class_limit)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, plan.getName());
            stmt.setString(2, plan.getDescription());
            stmt.setDouble(3, plan.getPrice());
            stmt.setInt(4, plan.getDurationDays());
            stmt.setString(5, plan.getInclusions());

            if (plan.getClassLimit() == 0) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, plan.getClassLimit());
            }

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean update(MembershipPlan plan) {

        String sql = """
                UPDATE membership_plans
                SET name = ?,
                    description = ?,
                    price = ?,
                    duration_days = ?,
                    inclusions = ?,
                    class_limit = ?,
                    updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """;

        try (
                Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, plan.getName());
            stmt.setString(2, plan.getDescription());
            stmt.setDouble(3, plan.getPrice());
            stmt.setInt(4, plan.getDurationDays());
            stmt.setString(5, plan.getInclusions());

            if (plan.getClassLimit() == 0) {
                stmt.setNull(6, Types.INTEGER);
            } else {
                stmt.setInt(6, plan.getClassLimit());
            }

            stmt.setInt(7, plan.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean delete(int id) {

        String sql = "UPDATE membership_plans SET is_archived = 1 WHERE id = ?";

        try (
                Connection conn = DbConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}

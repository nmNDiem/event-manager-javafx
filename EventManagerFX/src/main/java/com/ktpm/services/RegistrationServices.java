/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Event;
import com.ktpm.pojo.JdbcUtils;
import com.ktpm.pojo.Registration;
import com.ktpm.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class RegistrationServices {

    public boolean register(Event e, User u) {
        String insertSql = "INSERT INTO registration (event_id, user_id, payment_amount) VALUES (?, ?, ?)";
        String updateTicketSql = "UPDATE event SET available_tickets = available_tickets - 1 WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement insertStm = conn.prepareStatement(insertSql); PreparedStatement updateStm = conn.prepareStatement(updateTicketSql)) {

            conn.setAutoCommit(false);

            insertStm.setInt(1, e.getId());
            insertStm.setInt(2, u.getId());
            insertStm.setBigDecimal(3, e.getPrice());
            insertStm.executeUpdate();

            updateStm.setInt(1, e.getId());
            updateStm.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isUserRegistered(int eventId, int userId) {
        String sql = "SELECT COUNT(*) FROM registration WHERE event_id = ? AND user_id = ? AND payment_status = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, eventId);
            stm.setInt(2, userId);
            stm.setString(3, "PAID");

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean hasTimeConflict(int userId, LocalDateTime newStart, LocalDateTime newEnd) {
        String sql = "SELECT COUNT(*) "
                + "FROM event_registration er "
                + "JOIN event e ON er.event_id = e.id "
                + "WHERE er.user_id = ? "
                + "AND ("
                + "(? BETWEEN e.start_time AND e.end_time) "
                + "OR (? BETWEEN e.start_time AND e.end_time) "
                + "OR (e.start_time BETWEEN ? AND ?))";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, userId);
            stm.setTimestamp(2, Timestamp.valueOf(newStart));
            stm.setTimestamp(3, Timestamp.valueOf(newEnd));
            stm.setTimestamp(4, Timestamp.valueOf(newStart));
            stm.setTimestamp(5, Timestamp.valueOf(newEnd));

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<Registration> getRegsByEventId(int eventId, String paymentStatus) {
        List<Registration> registrationList = new ArrayList<>();
        String sql = "SELECT * FROM registration WHERE event_id = ? AND payment_status = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, eventId);
            stm.setString(2, paymentStatus);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Registration r = new Registration();
                r.setId(rs.getInt("id"));
                r.setEventId(rs.getInt("event_id"));
                r.setUserId(rs.getInt("user_id"));
                r.setRegistrationTime(rs.getTimestamp("registration_time").toLocalDateTime());
                r.setPaymentAmount(rs.getBigDecimal("payment_amount"));
                r.setPaymentStatus(rs.getString("payment_status"));

                registrationList.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registrationList;
    }

    public boolean updatePaymentStatus(Registration reg) {
        String sql = "UPDATE registration SET payment_status = ? WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, "REFUNDED");
            stm.setInt(2, reg.getId());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

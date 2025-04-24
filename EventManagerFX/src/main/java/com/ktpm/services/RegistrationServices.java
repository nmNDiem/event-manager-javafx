/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Event;
import com.ktpm.pojo.JdbcUtils;
import com.ktpm.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class RegistrationServices {

    public boolean register(Event e, User u) {
        String insertSql = "INSERT INTO registration (event_id, user_id) VALUES (?, ?)";
        String updateTicketSql = "UPDATE event SET available_tickets = available_tickets - 1 WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement insertStm = conn.prepareStatement(insertSql); PreparedStatement updateStm = conn.prepareStatement(updateTicketSql)) {

            conn.setAutoCommit(false);

            insertStm.setInt(1, e.getId());
            insertStm.setInt(2, u.getId());
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
        String sql = "SELECT COUNT(*) FROM registration WHERE event_id = ? AND user_id = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, eventId);
            stm.setInt(2, userId);

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
    
    public boolean hasTimeConflict(int userId, LocalDateTime newStart, LocalDateTime newEnd){
    String sql = "SELECT COUNT(*) "
            + "FROM event_registration er "
            + "JOIN event e ON er.event_id = e.id "
            + "WHERE er.user_id = ? "
            + "AND ("
            + "(? BETWEEN e.start_time AND e.end_time) "
            + "OR (? BETWEEN e.start_time AND e.end_time) "
            + "OR (e.start_time BETWEEN ? AND ?))";

    try (Connection conn = JdbcUtils.getConn();
         PreparedStatement stm = conn.prepareStatement(sql)) {

        stm.setInt(1, userId);
        stm.setTimestamp(2, Timestamp.valueOf(newStart));
        stm.setTimestamp(3, Timestamp.valueOf(newEnd));
        stm.setTimestamp(4, Timestamp.valueOf(newStart));
        stm.setTimestamp(5, Timestamp.valueOf(newEnd));

        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    }   catch (SQLException ex) {
            ex.printStackTrace();
        }

    return false;
}


}

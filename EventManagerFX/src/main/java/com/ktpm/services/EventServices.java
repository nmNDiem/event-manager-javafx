/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Event;
import com.ktpm.pojo.JdbcUtils;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public class EventServices {

    public ObservableList<Event> getEvents() throws SQLException {
        ObservableList<Event> events = FXCollections.observableArrayList();
        String sql = "SELECT *  FROM event";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int categoryId = rs.getInt("category_id");
                String name = rs.getString("name");
                int locationId = rs.getInt("location_id");
                LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("end_time").toLocalDateTime();
                int availableTickets = rs.getInt("available_tickets");
                BigDecimal price = rs.getBigDecimal("price");
                String imageUrl = rs.getString("image_url");
                String description = rs.getString("description");

                events.add(new Event(id, categoryId, name, locationId, startTime, endTime, availableTickets, price, imageUrl, description));
            }
        }

        return events;
    }

    public boolean addEvent(Event e) {
        String sql = "INSERT INTO event (category_id, name, location_id, "
                + "start_time, end_time, available_tickets, price, image_url, description) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, e.getCategoryId());
            stm.setString(2, e.getName());
            stm.setInt(3, e.getLocationId());
            stm.setTimestamp(4, Timestamp.valueOf(e.getStartTime()));
            stm.setTimestamp(5, Timestamp.valueOf(e.getEndTime()));
            stm.setInt(6, e.getAvailableTickets());
            stm.setBigDecimal(7, e.getPrice());
            stm.setString(8, e.getImageUrl());
            stm.setString(9, e.getDescription());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean isEventTimeConflict(int locationId, LocalDateTime startTime, LocalDateTime endTime) {
    String sql = "SELECT COUNT(*) FROM event WHERE location_id = ? AND (" +
                 "(start_time < ? AND end_time > ?) OR " +
                 "(start_time > ? AND end_time < ?) OR " +
                 "(start_time >= ? AND end_time <= ?) OR " +
                 "(start_time <= ? AND end_time >= ?))";
    
    try (Connection conn = JdbcUtils.getConn();
         PreparedStatement stm = conn.prepareStatement(sql)) {
        
        stm.setInt(1, locationId);
        stm.setTimestamp(2, Timestamp.valueOf(startTime));
        stm.setTimestamp(3, Timestamp.valueOf(endTime));
        stm.setTimestamp(4, Timestamp.valueOf(startTime));
        stm.setTimestamp(5, Timestamp.valueOf(endTime));
        stm.setTimestamp(6, Timestamp.valueOf(startTime));
        stm.setTimestamp(7, Timestamp.valueOf(endTime));
        stm.setTimestamp(8, Timestamp.valueOf(startTime));
        stm.setTimestamp(9, Timestamp.valueOf(endTime));
        
        ResultSet rs = stm.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;  // Nếu có xung đột, trả về true
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


}

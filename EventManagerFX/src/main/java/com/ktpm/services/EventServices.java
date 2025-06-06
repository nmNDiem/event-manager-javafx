/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Event;
import com.ktpm.pojo.JdbcUtils;
import com.ktpm.pojo.Location;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
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
                boolean isActive = rs.getBoolean("is_active");

                events.add(new Event(id, categoryId, name, locationId, startTime, endTime,
                        availableTickets, price, imageUrl, description, isActive));
            }
        }
        return events;
    }

    public ObservableList<Event> getEventsForTomorrow() throws SQLException {
        ObservableList<Event> events = FXCollections.observableArrayList();

        String sql = "SELECT * FROM event WHERE DATE(start_time) = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            LocalDate tomorrow = LocalDate.now().plusDays(1);
            stm.setDate(1, Date.valueOf(tomorrow));

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
                boolean isActive = rs.getBoolean("is_active");

                events.add(new Event(id, categoryId, name, locationId, startTime, endTime,
                        availableTickets, price, imageUrl, description, isActive));
            }
        }

        return events;
    }
    
    public Event getEventById(int eventId) throws SQLException {
        Event event = null;
        String sql = "SELECT * FROM event WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, eventId);

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
                boolean isActive = rs.getBoolean("is_active");

                event = new Event(id, categoryId, name, locationId, startTime, endTime,
                        availableTickets, price, imageUrl, description, isActive);
            }
        }

        return event;
    }

    public boolean addEvent(Event e) {
        String sql = "INSERT INTO event (category_id, name, location_id, "
                + "start_time, end_time, available_tickets, price, image_url, description, is_active) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            stm.setBoolean(10, true);

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateEvent(Event e) {
        String sql = "UPDATE event SET category_id=?, name=?, location_id=?, start_time=?, end_time=?, "
                + "available_tickets=?, price=?, image_url=?, description=? WHERE id=?";

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
            stm.setInt(10, e.getId());

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteEventById(int id) {
        String sql = "DELETE FROM event WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql);) {

            stm.setInt(1, id);
            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean isStartDateAfterNow(LocalDate startDate) {
        if (startDate.isAfter(LocalDate.now())) {
            return true;
        }
        return false;
    }

    public boolean isEventLocationTimeConflict(int eventId, int locationId, LocalDateTime startTime, LocalDateTime endTime) {
        String sql = "SELECT COUNT(*) FROM event WHERE location_id = ? "
                // loại trừ chính event hiện tại (khi sửa),
                // nếu thêm mới thì set eventId = 0 (vì không có eventId nào là 0)
                + "AND id != ? "
                // start (cũ) < end   (mới)
                // end   (cũ) > start (mới)
                + "AND start_time < ? AND end_time > ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setInt(1, locationId);
            stm.setInt(2, eventId);
            stm.setTimestamp(3, Timestamp.valueOf(endTime));
            stm.setTimestamp(4, Timestamp.valueOf(startTime));

            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;   // nếu đếm được > 0 event trùng thì return true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean checkTicketLimit(int tickets, Location location) {
        if (tickets < location.getCapacity()) {
            return true;
        }
        return false;
    }

    public boolean setEventActive(int eventId, boolean isActive) {
        String sql = "UPDATE event SET is_active = ? WHERE id = ?";
        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {
            stm.setBoolean(1, isActive);
            stm.setInt(2, eventId);

            return stm.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

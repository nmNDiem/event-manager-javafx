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
                int categoryId = rs.getInt("category_id");
                String name = rs.getString("name");
                String location = rs.getString("location");
                LocalDateTime startTime = rs.getTimestamp("start_time").toLocalDateTime();
                LocalDateTime endTime = rs.getTimestamp("end_time").toLocalDateTime();
                int capacity = rs.getInt("capacity");
                int availableTickets = rs.getInt("available_tickets");
                BigDecimal price = rs.getBigDecimal("price");
                String image_url = rs.getString("image_url");
                String description = rs.getString("description");

                events.add(new Event(categoryId, name, location, startTime, endTime, capacity, availableTickets, price, image_url, description));
            }
        }
        
        return events;

    }
}

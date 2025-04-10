/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.JdbcUtils;
import com.ktpm.pojo.Location;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author admin
 */
public class LocationServices {

    public ObservableList<Location> getLocations() throws SQLException {
        ObservableList<Location> locations = FXCollections.observableArrayList();
        String sql = "SELECT * FROM location";

        try (Connection conn = JdbcUtils.getConn(); 
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                int capacity = rs.getInt("capacity");
                
                locations.add(new Location(id, name, address, capacity));
            }
        }
        return locations;
    }
    
    public Location getLocationById(int locationId) throws SQLException {
        Location location = null;
        String sql = "SELECT * FROM location WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn(); 
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setInt(1, locationId);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String address = rs.getString("address");
                int capacity = rs.getInt("capacity");
                
                location = new Location(id, name, address, capacity);
            }
        }
        return location;
    }
}

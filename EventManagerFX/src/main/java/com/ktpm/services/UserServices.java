/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.JdbcUtils;
import com.ktpm.pojo.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author admin
 */
public class UserServices {
    public boolean addUser(User user) throws SQLException {
        String sql = "INSERT INTO user (full_name, phone, email, password) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setString(1, user.getFullName());
            stm.setString(2, user.getPhone());
            stm.setString(3, user.getEmail());
            stm.setString(4, hashPassword(user.getPassword()));
            
            int result = stm.executeUpdate();
            
            return result > 0;
        }
    }
    
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
    
    public boolean authenticate(String email, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";
        
        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setString(1, email);
            ResultSet user = stm.executeQuery();
            
            if (user.next()) {
                String hashedPassword = user.getString("password");
                return BCrypt.checkpw(password, hashedPassword);
            }
        }
        
        return false;
    }
}
;
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

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

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

    public User authenticate(String email, String password) throws SQLException {
        String sql = "SELECT * FROM user WHERE email = ?";

        try (Connection conn = JdbcUtils.getConn(); PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, email);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                // So sánh mật khẩu nhập vào với mật khẩu đã mã hóa
                if (BCrypt.checkpw(password, hashedPassword)) {
                    // Tạo đối tượng User nếu xác thực thành công
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setFullName(rs.getString("full_name"));
                    u.setPhone(rs.getString("phone"));
                    u.setEmail(rs.getString("email"));
                    u.setPassword(rs.getString("password")); // có thể không cần nếu không dùng lại
                    u.setRole(rs.getString("role"));
                    return u;
                }
            }
        }

        return null;
    }
}
;

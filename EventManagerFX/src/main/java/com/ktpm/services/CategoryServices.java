/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ktpm.services;

import com.ktpm.pojo.Category;
import com.ktpm.pojo.JdbcUtils;
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
public class CategoryServices {

    public ObservableList<Category> getCates() throws SQLException {
        ObservableList<Category> cates = FXCollections.observableArrayList();
        String sql = "SELECT * FROM category";
        
        try (Connection conn = JdbcUtils.getConn();
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                
                cates.add(new Category(id, name));
            }
        }
        
        return cates;
    }

    public Category getCateById(int cateId) throws SQLException {
        Category cate = null;
        String sql = "SELECT * FROM category WHERE id = ?";

        try (Connection conn = JdbcUtils.getConn(); 
             PreparedStatement stm = conn.prepareStatement(sql)) {
            
            stm.setInt(1, cateId);
            ResultSet rs = stm.executeQuery();
            while(rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                
                cate = new Category(id, name);
            }
        }
        return cate;
    }
}

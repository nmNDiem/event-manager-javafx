/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class AdminHomeController implements Initializable {
    @FXML Button btnEvent;
    @FXML Button btnLocaion;
    @FXML AnchorPane adminMainContent;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadAdminEventView();
    }    
    
    public void loadAdminEventView() {
        loadView("AdminEvent");
    }
    
    public void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile + ".fxml"));
            adminMainContent.getChildren().setAll(view);
        } catch (IOException ex) {
            Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
            Utils.showAlert("Lỗi: Không thể mở màn hình này!");
        }
    }
    
        @FXML
    public void logoutAdminHander() {
        try {
            Utils.setCurrentUser(null);
            App.setScene("Login");
        } catch (IOException ex) {
            Utils.showAlert("Xảy ra lỗi khi đăng xuất!");
            ex.printStackTrace();
        }
    }
}

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
    @FXML AnchorPane mainContent;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadEventView();
    }    
    
    public void loadEventView() {
        loadView("AdminEvent");
    }
    
    public void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile + ".fxml"));
            mainContent.getChildren().setAll(view);
        } catch (IOException ex) {
            Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
            Utils.showAlert("Lỗi: Không thể mở màn hình này!");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import com.ktpm.pojo.Category;
import com.ktpm.pojo.Event;
import com.ktpm.services.CategoryServices;
import com.ktpm.services.EventServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class HomeController implements Initializable {
    @FXML
    Label lbHello;
    @FXML AnchorPane mainContent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbHello.setText("Xin chào, " + Utils.getCurrentUser().getFullName() + "!");
        loadEventView();
    }
    
    public void loadEventView() {
        loadView("Event");
    }
    
    public void loadNotiView() {
        loadView("Noti");
    }

    public void loadView(String fxmlFile) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource(fxmlFile + ".fxml"));
            mainContent.getChildren().setAll(view);
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            Utils.showAlert("Lỗi: Không thể mở màn hình này!");
        }
    }

    @FXML
    public void logoutHander() {
        try {
            Utils.setCurrentUser(null);
            App.setScene("Login");
        } catch (IOException ex) {
            Utils.showAlert("Xảy ra lỗi khi đăng xuất!");
            ex.printStackTrace();
        }
    }
}

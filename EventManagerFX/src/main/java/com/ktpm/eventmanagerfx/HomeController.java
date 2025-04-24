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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    HBox cateContainer;
    @FXML
    FlowPane eventContainer;
    @FXML
    Label lbHello;

    CategoryServices cateServices = new CategoryServices();
    EventServices eventServices = new EventServices();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lbHello.setText("Xin chào, " + Utils.getCurrentUser().getFullName());
        loadCates();
        loadEvents();
    }

    public void loadCates() {
        try {
            ObservableList<Category> cates = cateServices.getCates();

            for (Category c : cates) {
                Button cateBtn = new Button(c.getName());

                cateBtn.getStyleClass().add("btn-cate");
                HBox.setMargin(cateBtn, new Insets(0, 0, 0, 20));

                cateContainer.getChildren().add(cateBtn);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadEvents() {
        try {
            ObservableList<Event> events = eventServices.getEvents();
            for (Event e : events) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("EventCard.fxml"));

                VBox eventCard = loader.load();
                EventCardController controller = loader.getController();

                controller.setEventData(e.getImageUrl(), e.getName(), e.getStartTime());
                controller.setEvent(e);

                eventContainer.getChildren().add(eventCard);
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
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

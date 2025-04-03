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
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class HomeController implements Initializable {

    @FXML
    VBox cateContainer;
    @FXML
    FlowPane eventContainer;

    CategoryServices cateServices = new CategoryServices();
    EventServices eventServices = new EventServices();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadCates();
        loadEvents();
    }

    public void loadCates() {
        try {
            ObservableList<Category> cates = cateServices.getCates();

            for (Category c : cates) {
                Button cateBtn = new Button(c.getName());

                cateBtn.getStyleClass().add("btn-cate");

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

                controller.setEventData(e.getImage_url(), e.getName(), e.getStartTime().toString());

                // bo goc event card
                Rectangle clip = new Rectangle(0, 0, 250, 240);
                clip.setArcWidth(20);
                clip.setArcHeight(20);
                eventCard.setClip(clip);

                eventContainer.getChildren().add(eventCard);
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

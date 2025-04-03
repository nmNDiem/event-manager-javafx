/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class EventCardController implements Initializable {

    @FXML
    ImageView imgView;
    @FXML
    Label lbName;
    @FXML
    Label lbStartTime;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setEventData(String imgUrl, String name, String startTime) {
        imgView.setImage(new Image(imgUrl));
        lbName.setText(name);
        lbStartTime.setText(startTime);
    }
}

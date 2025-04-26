/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class NotiCardController implements Initializable {

    @FXML Label lbSubject;
    @FXML Label lbMessage;
    @FXML Label lbSentAt;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    public void setNotiData(String subject, String message, LocalDateTime sentAt) {
        lbSubject.setText(subject);
        lbMessage.setText(message);
        lbSentAt.setText(Utils.formatDateTime(sentAt));
    }
    
}

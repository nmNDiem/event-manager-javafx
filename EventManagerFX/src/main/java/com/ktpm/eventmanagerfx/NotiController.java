/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import com.ktpm.pojo.Notification;
import com.ktpm.pojo.User;
import com.ktpm.services.NotiServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class NotiController implements Initializable {

    @FXML
    FlowPane notiContainer;
    @FXML
    Label lbEmpty;
    NotiServices notiServices = new NotiServices();
    User user = Utils.getCurrentUser();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadNotis();
    }

    @FXML
    public void loadNotis() {
        try {
            List<Notification> notiList = notiServices.getNotiByUserId(user.getId());
            if (notiList.isEmpty()) {
                lbEmpty.setManaged(true);
                lbEmpty.setVisible(true);
                return;
            }
            lbEmpty.setManaged(false);
            lbEmpty.setVisible(false);
            for (Notification n : notiList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("NotiCard.fxml"));

                AnchorPane notiCard = loader.load();
                NotiCardController controller = loader.getController();

                controller.setNotiData(n.getSubject(), n.getMessage(), n.getSentAt());

                notiContainer.getChildren().add(notiCard);
            }
        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

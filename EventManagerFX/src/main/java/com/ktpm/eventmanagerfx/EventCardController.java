/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import com.ktpm.pojo.Event;
import com.ktpm.pojo.User;
import com.ktpm.services.RegistrationServices;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
    @FXML
    Label lbPrice;

    private Event event;

    RegistrationServices regisService = new RegistrationServices();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void setEventData(String imgUrl, String name, LocalDateTime startTime, BigDecimal price) {
        imgView.setImage(new Image(imgUrl));
        lbName.setText(name);
        lbStartTime.setText(Utils.formatDateTime(startTime));
        lbPrice.setText(Utils.formatCurrency(price));

        // bo goc event card
        Rectangle clip = new Rectangle(0, 0, 200, 210);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imgView.setClip(clip);
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @FXML
    public void registerHandler() {
        if (!canRegister(event, Utils.getCurrentUser()))
            return;
        
        Optional<ButtonType> result = Utils.showConfirmAlert(
                "Thanh toán " + Utils.formatCurrency(event.getPrice()) + "?");
        if (result.isPresent() && result.get() == ButtonType.YES) {

            User currentUser = Utils.getCurrentUser();
            if (regisService.register(event, currentUser)) {
                Utils.showAlert("Mua vé thành công!");
            } else {
                Utils.showAlert("Mua vé thất bại!");
            }
        }

    }

    public boolean canRegister(Event event, User currentUser) {
        // - Hết vé
        if (!regisService.hasAvailableTickets(event)) {
            Utils.showAlert("Sự kiện đã hết vé!");
            return false;
        }

        // - Đã đky sự kiện này
        if (regisService.isUserRegistered(event.getId(), currentUser.getId())) {
            Utils.showAlert("Bạn đã đăng ký sự kiện này trước đó rồi!");
            return false;
        }
        
        // - Không thể đky trước thời gian bắt đầu < 60p
        if (LocalDateTime.now().isAfter(event.getStartTime().minusMinutes(60))) {
            Utils.showAlert("Không thể đăng ký. Sự kiện sẽ bắt đầu trong vòng 60 phút nũa.");
            return false;
        }

        // - Trùng giờ với sự kiện khác đã đky
        if (regisService.hasTimeConflict(currentUser.getId(), event.getStartTime(), event.getEndTime())) {
            Utils.showAlert("Bạn đã đăng ký sự kiện khác vào thời gian này!");
            return false;
        }

        return true;
    }

}

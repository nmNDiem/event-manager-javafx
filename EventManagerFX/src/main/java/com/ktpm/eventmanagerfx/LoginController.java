/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import com.ktpm.pojo.User;
import com.ktpm.services.NotiServices;
import com.ktpm.services.UserServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    @FXML
    TextField txtEmailLogin;
    @FXML
    PasswordField txtPasswordLogin;
    @FXML
    Button btnLogin;

    UserServices userServices = new UserServices();
    NotiServices notiServices = new NotiServices();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // disable button Đăng nhập khi chưa điền đầy đủ thông tin
        btnLogin.disableProperty().bind(
                txtEmailLogin.textProperty().isEmpty()
                        .or(txtPasswordLogin.textProperty().isEmpty())
        );

        txtPasswordLogin.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                try {
                    loginHandler();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loginHandler() throws SQLException, IOException {
        String email = txtEmailLogin.getText().trim();
        String password = txtPasswordLogin.getText().trim();

        User user = userServices.authenticate(email, password);

        if (user != null) {
            Utils.setCurrentUser(user);

            if (user.getRole().equals("ADMIN")) {
                App.setScene("AdminHome");
            } else {
                App.setScene("Home");
                notiServices.sendReminder(user.getId());
            }
        } else {
            Utils.showAlert("Email hoặc mật khẩu không đúng!");
        }
    }

    public void goToSignUp() throws IOException {
        App.setScene("SignUp");
    }
}

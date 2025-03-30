/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

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
    @FXML TextField txtEmailLogin;
    @FXML PasswordField txtPasswordLogin;
    @FXML Button btnLogin;
    
    UserServices userServices = new UserServices();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // disable button Đăng nhập khi chưa điền đầy đủ thông tin
        btnLogin.disableProperty().bind(
                txtEmailLogin.textProperty().isEmpty()
            .or(txtPasswordLogin.textProperty().isEmpty())
        );
    }

    public void loginHandler() throws SQLException {
        String email = txtEmailLogin.getText().trim();
        String password = txtPasswordLogin.getText().trim();
        
        boolean success = userServices.authenticate(email, password);
        
        if (success) {
            Utils.getAlert("Đăng nhập thành công!");
        } else {
            Utils.getAlert("Email hoặc mật khẩu không đúng!");
        }
    }
    
    public void goToSignUp() throws IOException {
        App.setRoot("SignUp");
    }
}

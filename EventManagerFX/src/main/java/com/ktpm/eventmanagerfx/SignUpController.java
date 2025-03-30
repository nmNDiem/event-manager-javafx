/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.ktpm.eventmanagerfx;

import com.ktpm.pojo.User;
import com.ktpm.services.UserServices;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class SignUpController implements Initializable {

    @FXML
    TextField txtFullName;
    @FXML
    TextField txtPhone;
    @FXML
    TextField txtEmail;
    @FXML
    PasswordField txtPassword;
    @FXML
    PasswordField txtConfirmPassword;
    @FXML
    Button btnSignUp;

    UserServices userService = new UserServices();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // disable button Đăng ký khi chưa điền đầy đủ thông tin
        btnSignUp.disableProperty().bind(
                txtFullName.textProperty().isEmpty()
                        .or(txtPhone.textProperty().isEmpty())
                        .or(txtEmail.textProperty().isEmpty())
                        .or(txtPassword.textProperty().isEmpty())
                        .or(txtConfirmPassword.textProperty().isEmpty())
        );
    }

    public void signUpHandler(ActionEvent e) throws SQLException {
        String fullName = txtFullName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        if (!validateInput(fullName, phone, email, password, confirmPassword)) {
            return;
        }

        User user = new User(fullName, phone, email, password);
        boolean success = userService.addUser(user);

        if (success) {
            Utils.getAlert("Đăng ký thành công!");
        } else {
            Utils.getAlert("Đăng ký thất bại!");
        }
    }

    public boolean validateInput(String fullName, String phone, String email, String password, String confirmPassword) {
        if (!password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            Utils.getAlert("Mật khẩu phải có ít nhất 8 ký tự, 1 chữ hoa, 1 số, 1 ký tự đặc biệt!");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Utils.getAlert("Mật khẩu xác nhận không khớp!");
            return false;
        }

        return true;
    }

    public void goToLogin() throws IOException {
        App.setRoot("Login");
    }
}

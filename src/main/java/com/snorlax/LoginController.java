package com.snorlax;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfEmail;

    @FXML
    protected void submitClicked(ActionEvent e) {
        System.out.println("SubmitClicked");
        String password = pfPassword.getText();
        String email = tfEmail.getText();
        System.out.println(password + " - " + email);
        if (password.length() < 1 || email.length() < 1){
            Alerts.showAlertMessage(Alert.AlertType.WARNING, "Error Log In", "Password or email are empty");
        }
        if (email.equals("email") && password.equals("password")){
            System.out.println("Login Successful");
        }
    }
}

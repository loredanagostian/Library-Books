package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.loose.fis.sre.exceptions.InvalidPassword;
import org.loose.fis.sre.exceptions.UsernameNotFound;
import org.loose.fis.sre.services.UserService;

public class LoginController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Text loginMessage;

    @FXML
    public void handleLoginAction() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (user == null || user.isEmpty()) {
            loginMessage.setText("Please type in a username!");
            return;
        }

        if (pass == null || pass.isEmpty()) {
            loginMessage.setText("Password cannot be empty!");
            return;
        }

        try {
            UserService.loginUser(usernameField.getText(), passwordField.getText());
            //LoginMessage.setText("Account created successfully!");
        } catch (UsernameNotFound e1) {
            loginMessage.setText(e1.getMessage());
        } catch (InvalidPassword e2) {
            loginMessage.setText(e2.getMessage());
        }
    }
}

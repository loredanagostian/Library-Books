package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.InvalidPassword;
import org.loose.fis.sre.exceptions.UsernameNotFound;
import org.loose.fis.sre.services.stageOptimise;
import org.loose.fis.sre.services.userDB;

import java.io.IOException;
import java.sql.SQLException;


public class LoginController {
    @FXML
    public Button connectButton;
    @FXML
    private PasswordField passwordPassField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label showMessage;

    public void connectButton(javafx.event.ActionEvent actionEvent) {
        String user = usernameTextField.getText();
        String pass = passwordPassField.getText();

        if (user.isEmpty()) {
            showMessage.setText("Please type in a username!");
            return;
        }

        if (pass.isEmpty()) {
            showMessage.setText("Password cannot be empty!");
            return;
        }

        try {
            String role = userDB.loginUser(user, pass);
            showMessage.setText("Login successfully!");

            if (role.equals("Customer"))
                stageOptimise.switchToStage("customer.fxml", "Customer", actionEvent);

            if (role.equals("Librarian"))
                stageOptimise.switchToStage("librarian.fxml", "Library", actionEvent);

        } catch (UsernameNotFound | InvalidPassword | SQLException e) {
            showMessage.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void registerButton(javafx.event.ActionEvent actionEvent) throws IOException {
        stageOptimise.switchToStage("register.fxml", "Register", actionEvent);
    }
}
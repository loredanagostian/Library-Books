package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.stageOptimise;
import org.loose.fis.sre.services.userDB;

import java.io.IOException;
import java.sql.SQLException;

public class RegistrationController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox roleBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private Label showMessage;

    public void initialize() {
        roleBox.getItems().addAll("Customer", "Librarian");
        roleBox.setValue("");
    }

    public void registerButton(javafx.event.ActionEvent actionEvent) {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        String role = roleBox.getValue().toString();
        String name = nameField.getText();
        String email = emailField.getText();

        if(user.isEmpty() || pass.isEmpty() || role.equals("") || name.isEmpty() || email.isEmpty()){
            showMessage.setText("You must fill in all the required details!");
            return;
        }

        try {
            userDB.insertUser(user, pass, role, name, email);
            showMessage.setText("Account created successfully!");

            if(role.equals("Customer"))
                stageOptimise.switchToStage("customer.fxml", "Customer View", actionEvent);
            else
                stageOptimise.switchToStage("librarian.fxml", "Library", actionEvent);
        } catch (UsernameAlreadyExistsException | SQLException | IOException e) {
            showMessage.setText(e.getMessage());
        }
    }

    public void backToLogin(javafx.event.ActionEvent actionEvent) throws IOException {
        stageOptimise.switchToStage("login.fxml", "Log In", actionEvent);
    }
}
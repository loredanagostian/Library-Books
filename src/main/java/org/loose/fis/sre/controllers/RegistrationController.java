package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
            userDB.insertUser(user, pass, role, name, email, "users");
            showMessage.setText("Account created successfully!");

            if(role.equals("Customer")) {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("customer.fxml"));
                Pane root = null;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                CustomerController secondController = fxmlLoader.getController();
                secondController.populateWindow(user);

                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                stage.setTitle("Customer View");
                assert root != null;
                stage.setScene(new Scene(root));
                stage.show();
            }
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
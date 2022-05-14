package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.InvalidPassword;
import org.loose.fis.sre.exceptions.UsernameNotFound;
import org.loose.fis.sre.services.userDB;

import java.io.IOException;
import java.sql.*;


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
            userDB.loginUser(user, pass);
            showMessage.setText("Login successfully!");

            String role = userDB.Role(user);

            if (role.equals("Customer")) {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("customer.fxml"));
                Pane root = fxmlLoader.load();
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                stage.setTitle("Customer");
                stage.setScene(new Scene(root, 650, 450));
                stage.show();
            }

            if (role.equals("Librarian")) {
                Stage stage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("librarian.fxml"));
                Pane root = fxmlLoader.load();
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                stage.setTitle("Librarian");
                stage.setScene(new Scene(root, 650, 450));
                stage.show();
            }

        } catch (UsernameNotFound | InvalidPassword | SQLException e) {
            showMessage.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void registerButton(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("register.fxml"));
        Pane root = fxmlLoader.load();
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        stage.setTitle("Register");
        stage.setScene(new Scene(root, 650, 450));
        stage.show();
    }
}
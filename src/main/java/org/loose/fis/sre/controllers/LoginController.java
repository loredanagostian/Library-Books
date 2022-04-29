package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.InvalidCredentials;
import org.loose.fis.sre.exceptions.InvalidPassword;
import org.loose.fis.sre.exceptions.UsernameNotFound;
import org.loose.fis.sre.services.userDB;
import java.sql.*;


public class LoginController {
    @FXML
    public Button connectButton;
    @FXML
    private Button registerButton;
    @FXML
    private PasswordField passwordPassField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label showMessage;

//    Stage dialogStage = new Stage();
//    Scene scene;

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
        } catch (InvalidCredentials | UsernameNotFound | InvalidPassword | SQLException e) {
            showMessage.setText(e.getMessage());
        }
    }

    //    @FXML
//    public void handleRegisterAction() {
//        try {
//            userDB.insertUser(usernameField.getText(), passwordField.getText(), (String) role.getValue());
//            registrationMessage.setText("Account created successfully!");
//        } catch (NoUsernameException e) {
//            registrationMessage.setText(e.getMessage());
//        } catch (NoPasswordException e) {
//            registrationMessage.setText(e.getMessage());
//        } catch (NoRoleSelectedException e) {
//            registrationMessage.setText(e.getMessage());
//        } catch (UsernameAlreadyExistsException e) {
//            registrationMessage.setText(e.getMessage());
//        }
//    }
//
//    @FXML
    public void switchStage_to_Customer_Home() {
//        Stage stage = new Stage();
//        //
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("OwnerHome.fxml"));
//        Pane root = fxmlLoader.load();
//        OwnerHomeController controller = fxmlLoader.getController();
//        controller.passOwnerNameText(usernameField.getText());
//        controller.populateTable();
//        //
//        stage.setTitle("Owner Home");
//        stage.setScene(new Scene(root, 650, 500));
//        stage.show();
    }

    //
    public void switchStage_to_Librarian_Home() {
//        Stage stage = new Stage();
//        //
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("ClientHome.fxml"));
//        Pane root = fxmlLoader.load();
//        ClientHomeController controller = fxmlLoader.getController();
//        controller.passClientNameText(usernameField.getText());
//        controller.populateTable();
//        //
//        stage.setTitle("Client Home");
//        stage.setScene(new Scene(root, 800, 500));
//        stage.show();
//
    }
}


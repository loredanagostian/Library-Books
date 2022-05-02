package org.loose.fis.sre.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.loose.fis.sre.model.Books;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class BookController implements Initializable {

    @FXML
    private Label authorField;

    @FXML
    public Button backButton;

    @FXML
    private Button buyButton;

    @FXML
    private Button rentButton;

    @FXML
    private Label titleField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void populateWindow(String title, String author){
        titleField.setText(title);
        authorField.setText(author);
    }

    public void backButton(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("customer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
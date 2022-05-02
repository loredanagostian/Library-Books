package org.loose.fis.sre.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.loose.fis.sre.model.Books;

import java.net.URL;
import java.util.ResourceBundle;

public class BookController implements Initializable {

    @FXML
    private Label authorField;

    @FXML
    private Button backButton;

    @FXML
    private Button buyButton;

    @FXML
    private AnchorPane pane;

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
}

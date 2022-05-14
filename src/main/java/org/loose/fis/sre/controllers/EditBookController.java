package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.bookDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class EditBookController {

    @FXML
    private TextField authorField;

    @FXML
    private ChoiceBox availabilityBox;

    @FXML
    private Button backButton;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button editButton;

    @FXML
    private TextField priceField;

    @FXML
    private ChoiceBox rentBuyBox;

    @FXML
    private TextField stockField;

    @FXML
    private TextField titleField;

    @FXML
    private Label book;

    @FXML
    private Label showMessage;

    public void initialize() {
        availabilityBox.getItems().addAll("Available", "Not Available");
        availabilityBox.setValue("");

        rentBuyBox.getItems().addAll("Rent", "Buy", "Rent and Buy");
        rentBuyBox.setValue("");
    }

    public void populateWindow(String title){
        book.setText("Edit " + "\"" + title + "\"");
    }

    @FXML
    public void backButton(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("librarian.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void editButton(javafx.event.ActionEvent actionEvent) {
        String title = titleField.getText();
        String author = authorField.getText();
        String description = descriptionField.getText();
        String price = priceField.getText();
        String rentBuy = rentBuyBox.getValue().toString();
        String stock = stockField.getText();
        String availability = availabilityBox.getValue().toString();

        String div[] = book.getText().split("\"");
        String bookTitle = div[1];

        try {
            bookDB.editBook(bookTitle, title, author, description, price, rentBuy, stock, availability);
            showMessage.setText("Book successfully edited!");
        }catch (SQLException e) {
            showMessage.setText(e.getMessage());
        }
    }

}

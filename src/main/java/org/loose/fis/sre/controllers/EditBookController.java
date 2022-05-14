package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.stageOptimise;

import java.io.IOException;
import java.sql.SQLException;

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
        stageOptimise.switchToStage("librarian.fxml", "Library", actionEvent);
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

        String[] div = book.getText().split("\"");
        String bookTitle = div[1];

        try {
            bookDB.editBook(bookTitle, title, author, description, price, rentBuy, stock, availability);
            showMessage.setText("Book successfully edited!");
        }catch (SQLException e) {
            showMessage.setText(e.getMessage());
        }
    }

}

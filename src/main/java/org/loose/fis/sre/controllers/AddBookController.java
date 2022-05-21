package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.loose.fis.sre.exceptions.BookAlreadyExistsException;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.stageOptimise;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class AddBookController {

    @FXML
    private Button addButton;

    @FXML
    private TextField authorField;

    @FXML
    private ChoiceBox availabilityBox;

    @FXML
    private Button backButton;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField priceField;

    @FXML
    private ChoiceBox rentBuyBox;

    @FXML
    private TextField stockField;

    @FXML
    private TextField titleField;

    @FXML
    private Label showMessage;

    public void initialize() {
        availabilityBox.getItems().addAll("Available", "Not Available");
        availabilityBox.setValue("");

        rentBuyBox.getItems().addAll("Rent", "Buy", "Rent and Buy");
        rentBuyBox.setValue("");
    }

    @FXML
    public void backButton(javafx.event.ActionEvent actionEvent) throws IOException {
        stageOptimise.switchToStage("librarian.fxml", "Library", actionEvent);
    }

    @FXML
    public void addButton(javafx.event.ActionEvent actionEvent) {
        String title = titleField.getText();
        String author = authorField.getText();
        String description = descriptionField.getText();
        String price = priceField.getText();
        String rentBuy = rentBuyBox.getValue().toString();
        String stock = stockField.getText();
        String availability = availabilityBox.getValue().toString();


        if (rentBuy.equals("Rent")) {
            if (title.isEmpty() || author.isEmpty() || description.isEmpty() || availability.equals("")){
                showMessage.setText("You must fill in all the required details!");
                return;
            }}
        else if (rentBuy.equals("Buy")) {
            if (title.isEmpty() || author.isEmpty() || description.isEmpty() || price.isEmpty() || stock.isEmpty()){
                showMessage.setText("You must fill in all the required details!");
                return;
            }}
        else {if (title.isEmpty() || author.isEmpty() || description.isEmpty() || rentBuy.equals("") || stock.isEmpty() || price.isEmpty() || availability.equals("")){
            showMessage.setText("You must fill in all the required details!");
            return;  }
         }

        try {
            if (price.isEmpty()) price = "0";
            if (stock.isEmpty()) stock = "0";
            bookDB.insertBook(title, author, description, price, rentBuy, stock, availability, "books");

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Confirmation");
            alert.setContentText("Book added successfully!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent())
                if(result.get() == ButtonType.OK)
                    stageOptimise.switchToStage("librarian.fxml", "Librarian View", actionEvent);

        }catch (BookAlreadyExistsException e) {
//            showMessage.setText(e.getMessage());

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("Error adding book");
            alert.setContentText(e.getMessage());
            alert.show();
        }catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

}

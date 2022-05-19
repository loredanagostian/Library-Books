package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.BookAlreadyRequested;
import org.loose.fis.sre.model.User;
import org.loose.fis.sre.services.rentDB;
import org.loose.fis.sre.services.stageOptimise;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

public class RentController {

    @FXML
    private TextField periodField;

    @FXML
    private Label showAuthor;

    @FXML
    private Label username;

    @FXML
    private Label showTitle;

    @FXML
    private Label showMessage;

    private String description;
    private Integer price;
    private Integer stock;

    @FXML
    void backButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("book.fxml"));
        Pane root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BookController secondController = fxmlLoader.getController();
        secondController.populateWindow(username.getText(), showTitle.getText(), showAuthor.getText(), description, price, stock);

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        stage.setTitle("Book");
        assert root != null;
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void requestButton(ActionEvent actionEvent) {
        if(periodField.getText().isEmpty() | periodField.getText().isBlank() | periodField.getText().equals("")){
            showMessage.setText("You have to set the period of renting.");
            return;
        }

        Integer period = Integer.valueOf(periodField.getText());

        try{
            rentDB.rentBook(username.getText(), showTitle.getText(), showAuthor.getText(), period);

            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Confirmation");
            alert.setContentText("Your rent request has been sent to the librarian!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent())
                if(result.get() == ButtonType.OK) {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("customer.fxml"));
                    Pane root = null;
                    try {
                        root = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    CustomerController secondController = fxmlLoader.getController();
                    secondController.populateWindow(username.getText());

                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    stage.setTitle("Customer View");
                    assert root != null;
                    stage.setScene(new Scene(root));
                    stage.show();
                }

        } catch (BookAlreadyRequested b) {
            showMessage.setText(b.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void populateWindow(String user, String title, String author, String description, String price, String stock){
        username.setText(user);
        showTitle.setText(title);
        showAuthor.setText(author);
        this.description = description;
        this.price = Integer.valueOf(price);
        this.stock = Integer.valueOf(stock);
    }
}
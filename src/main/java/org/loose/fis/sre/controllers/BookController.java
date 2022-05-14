package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.CartItemAlreadyExists;
import org.loose.fis.sre.exceptions.NoBookFound;
import org.loose.fis.sre.model.Books;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.cartDB;
import org.loose.fis.sre.services.stageOptimise;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BookController implements Initializable {
    @FXML
    public Button rentButton;

    @FXML
    private Label authorField;

    @FXML
    public Button backButton;

    @FXML
    public Button buyButton;

    public void rentButton(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("rent.fxml"));
        Pane root = fxmlLoader.load();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        RentController rentController = fxmlLoader.getController();
        rentController.setLabels(titleField.getText(), authorField.getText());

        stage.setTitle("Rent Request");
        stage.setScene(new Scene(root));
        stage.show();
    }

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
        stageOptimise.switchToStage("customer.fxml", "Customer View", actionEvent);
    }

    public void buyButton(javafx.event.ActionEvent actionEvent) throws SQLException, NoBookFound, IOException {
        String titleRequested = titleField.getText();
        String authorRequested = authorField.getText();

        try{
            Books book = bookDB.searchBook(titleRequested, authorRequested);
            cartDB.addCartItem(book.getTitle(), book.getAuthor(), book.getPrice());

            stageOptimise.switchToStage("cart.fxml", "Cart", actionEvent);
        } catch (CartItemAlreadyExists e) {
            e.printStackTrace();
        }
    }
}
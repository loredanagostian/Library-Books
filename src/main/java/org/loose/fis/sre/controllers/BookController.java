package org.loose.fis.sre.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.CartItemAlreadyExists;
import org.loose.fis.sre.exceptions.NoBookFound;
import org.loose.fis.sre.model.Books;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.cartDB;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
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

    public void rentButton(ActionEvent actionEvent){

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
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("customer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void buyButton(javafx.event.ActionEvent actionEvent) throws SQLException, NoBookFound, IOException {
        String titleRequested = titleField.getText();
        String authorRequested = authorField.getText();

        try{
            Books book = bookDB.searchBook(titleRequested, authorRequested);

            cartDB.addCartItem(book.getTitle(), book.getAuthor(), book.getPrice());

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("cart.fxml"));
            Pane root = fxmlLoader.load();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

            stage.setTitle("Cart");
            stage.setScene(new Scene(root));
            stage.show();

        } catch (CartItemAlreadyExists e) {
            e.printStackTrace();
        }
    }
}
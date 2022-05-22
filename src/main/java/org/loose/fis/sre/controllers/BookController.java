package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private Label username;

    @FXML
    public Button backButton;

    @FXML
    public Button buyButton;

    @FXML
    private Label titleField;

    @FXML
    private Label descriptionField;

    @FXML
    private Label priceField;

    @FXML
    private Label stockField;

    public void rentButton(ActionEvent actionEvent) throws IOException, SQLException, NoBookFound {

        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(stageOptimise.class.getClassLoader().getResource("rent.fxml"));
        Pane root = fxmlLoader.load();

        RentController secondController = fxmlLoader.getController();
        secondController.populateWindow(username.getText(), titleField.getText(), authorField.getText(), descriptionField.getText(), priceField.getText(), stockField.getText());

        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        stage.setTitle("Rent Request");
        stage.setScene(new Scene(root));
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void populateWindow(String user, String title, String author, String description, Integer price, Integer stock){
        username.setText(user);
        titleField.setText(title);
        authorField.setText(author);
        descriptionField.setText(description);
        priceField.setText(String.valueOf(price));
        stockField.setText(String.valueOf(stock));
    }

    public void backButton(javafx.event.ActionEvent actionEvent) throws IOException {
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

    public void buyButton(javafx.event.ActionEvent actionEvent) throws SQLException, NoBookFound, IOException {
        String titleRequested = titleField.getText();
        String authorRequested = authorField.getText();

        try{
            Books book = bookDB.searchBook(titleRequested, authorRequested, "books");
            cartDB.addCartItem(book.getTitle(), book.getAuthor(), book.getPrice(), username.getText(), "cart_items");

            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("cart.fxml"));
            Pane root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            CartController secondController = fxmlLoader.getController();
            secondController.populateWindow(username.getText());
            secondController.initialize2();

            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            stage.setTitle("Cart Items");
            assert root != null;
            stage.setScene(new Scene(root));
            stage.show();

        } catch (CartItemAlreadyExists e) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setHeaderText("Error adding item to cart");
            alert.setContentText("A book with the title " + titleField.getText() + " and author " + authorField.getText() + " already exists in the cart!");
            Button errorButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
            errorButton.setId("addItemError");
            alert.show();
        }
    }
}
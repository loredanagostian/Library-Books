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
import org.loose.fis.sre.exceptions.BookHas0Stock;
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

    public void rentButton(ActionEvent actionEvent) throws IOException, SQLException, NoBookFound {
        stageOptimise.switchToStageWithPopulateTitleAuthor(
                username.getText(),
                "rent.fxml",
                "Rent Request",
                titleField.getText(),
                authorField.getText(),
                false,
                "rent",
                actionEvent
        );
    }

    @FXML
    private Label titleField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void populateWindow(String user, String title, String author){
        username.setText(user);
        titleField.setText(title);
        authorField.setText(author);
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
            Books book = bookDB.searchBook(titleRequested, authorRequested);
            cartDB.addCartItem(book.getTitle(), book.getAuthor(), book.getPrice(), username.getText());

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
//            stageOptimise.switchToStageWithPopulateTitleAuthor(
//                    username.getText(),
//                    "cart.fxml",
//                    "Cart Items",
//                    titleField.getText(),
//                    authorField.getText(),
//                    false,
//                    "cart",
//                    actionEvent
//            );

        } catch (CartItemAlreadyExists | BookHas0Stock e) {
            e.printStackTrace();
        }
    }
}
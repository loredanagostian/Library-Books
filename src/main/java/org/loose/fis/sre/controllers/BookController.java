package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    public Button backButton;

    @FXML
    public Button buyButton;

    public void rentButton(ActionEvent actionEvent) throws IOException, SQLException, NoBookFound {
        stageOptimise.switchToStageWithPopulateTitleAuthor(
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
        } catch (CartItemAlreadyExists | BookHas0Stock e) {
            e.printStackTrace();
        }
    }
}
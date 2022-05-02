package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.loose.fis.sre.exceptions.NoBookFound;
import org.loose.fis.sre.model.Books;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.loose.fis.sre.services.bookDB;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    private TableColumn<Books, String> colAuthor;

    @FXML
    private TextField searchField;

    @FXML
    private Label showMessage;

    @FXML
    public Button searchButton;

    @FXML
    private TableView<Books> table;

    @FXML
    private TableColumn<Books, String> colTitle;

    @FXML
    private Button viewHistory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            table.setItems(null);
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            table.setItems(bookDB.getBooks());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchButton (){
        String toBeSearched = searchField.getText();

        if(toBeSearched.isBlank() | toBeSearched.isEmpty()){
            showMessage.setText("You have to type something.");
            return;
        }

        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("book.fxml"));
            Pane root = fxmlLoader.load();

            BookController secController = fxmlLoader.getController();
            secController.populateWindow(bookDB.getSearchedBooks(toBeSearched));

            stage.setTitle("Book");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (SQLException | NoBookFound e) {
            showMessage.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

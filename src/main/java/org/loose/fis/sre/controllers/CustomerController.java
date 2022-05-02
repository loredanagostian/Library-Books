package org.loose.fis.sre.controllers;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import okhttp3.Call;
import org.loose.fis.sre.exceptions.NoBookFound;
import org.loose.fis.sre.model.Books;

import org.loose.fis.sre.services.bookDB;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Comparator;
import java.util.Locale;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    @FXML
    private TableColumn<Books, String> colAuthor;

    @FXML
    private TableColumn<Books, String> colButton;

    @FXML
    private TextField searchField;

    @FXML
    private Label showMessage;

    @FXML
    public Button searchButton;

    @FXML
    private TableView<Books> table;

    @FXML
    private Button viewHistory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            table.setItems(null);
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            colButton.setCellValueFactory(new PropertyValueFactory<>(""));

            Callback<TableColumn<Books, String>, TableCell<Books, String>> cellFactory
                    = new Callback<>() {
                @Override
                public TableCell<Books, String> call(TableColumn<Books, String> param) {
                    return new TableCell<>() {
                        final Button btn = new Button();

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                Books book = getTableView().getItems().get(getIndex());
                                btn.setText(book.getTitle());

                                btn.setOnAction(actionEvent -> {
                                    Stage stage = new Stage();
                                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("book.fxml"));
                                    Pane root = null;
                                    try {
                                        root = fxmlLoader.load();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    BookController secondController = fxmlLoader.getController();
                                    secondController.populateWindow(book.getTitle(), book.getAuthor());

                                    stage.setTitle("Book");
                                    assert root != null;
                                    stage.setScene(new Scene(root));
                                    stage.show();
                                });

                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                }
            };
            colButton.setCellFactory(cellFactory);

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

            BookController secondController = fxmlLoader.getController();
            secondController.populateWindow(bookDB.getSearchedBooks(toBeSearched).get(0).getTitle(),
                                             bookDB.getSearchedBooks(toBeSearched).get(0).getAuthor());

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

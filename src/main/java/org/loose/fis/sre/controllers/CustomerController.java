package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.loose.fis.sre.exceptions.NoBookFound;
import org.loose.fis.sre.model.Books;

import org.loose.fis.sre.services.bookDB;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
    public Button viewCartButton;

    @FXML
    public Button logoutButton;

    @FXML
    private TableView<Books> table;

    @FXML
    void viewHistoryButton (javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("history.fxml"));
        Pane root = fxmlLoader.load();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        stage.setTitle("History");
        stage.setScene(new Scene(root));
        stage.show();
    }

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

                                    if(!book.getForBuy())
                                        secondController.buyButton.setVisible(false);

                                    if(!book.getForRent())
                                        secondController.rentButton.setVisible(false);

                                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
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

    public void searchButton (javafx.event.ActionEvent actionEvent){
        String toBeSearched = searchField.getText();

        if(toBeSearched.isBlank() | toBeSearched.isEmpty()){
            showMessage.setText("You have to type something.");
            return;
        }

        try{
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("book.fxml"));
            Pane root = fxmlLoader.load();
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

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

    public void logoutButton (javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("login.fxml"));
        Pane root = fxmlLoader.load();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        stage.setTitle("Log in");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void viewCartButton(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("cart.fxml"));
        Pane root = fxmlLoader.load();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        stage.setTitle("Cart");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
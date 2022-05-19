package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
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
import org.loose.fis.sre.model.Books;
import org.loose.fis.sre.model.RentItem;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.historyDB;
import org.loose.fis.sre.services.rentDB;
import org.loose.fis.sre.services.stageOptimise;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RequestsController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<RentItem, String> colAccept;

    @FXML
    private TableColumn<RentItem, String> colAuthor;

    @FXML
    private TableColumn<RentItem, String> colClient;

    @FXML
    private TableColumn<RentItem, String> colDecline;

    @FXML
    private TableColumn<RentItem, String> colTitle;

    @FXML
    private TableView<RentItem> table;

    @FXML
    private Label showMessage;

    @FXML
    public void backButton(javafx.event.ActionEvent actionEvent) throws IOException {
        stageOptimise.switchToStage("librarian.fxml", "Library", actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            table.setItems(null);
            colClient.setCellValueFactory(new PropertyValueFactory<>("client"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            colAccept.setCellValueFactory(new PropertyValueFactory<>(""));
            colDecline.setCellValueFactory(new PropertyValueFactory<>(""));

            Callback<TableColumn<RentItem, String>, TableCell<RentItem, String>> cellFactory = new Callback<>() {
                @Override
                public TableCell<RentItem, String> call(TableColumn<RentItem, String> param) {
                    return new TableCell<>() {
                        final Button btn = new Button("Accept");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                RentItem book = getTableView().getItems().get(getIndex());

                                btn.setOnAction(actionEvent -> {
                                    try {
                                        historyDB.insertRentedBook(book.getTitle(), book.getAuthor(), book.getPeriod(), book.getClient());
                                        showMessage.setText("The request from " + book.getClient() + " has been accepted!");
                                        table.setItems(rentDB.getBooks());

                                        rentDB.deleteBook(book.getTitle(), book.getAuthor(), book.getClient());
                                        table.setItems(rentDB.getBooks());

                                    }catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                }
            };
            colAccept.setCellFactory(cellFactory);
            table.setItems(rentDB.getBooks());


            Callback<TableColumn<RentItem, String>, TableCell<RentItem, String>> cellFactory2 = new Callback<>() {
                @Override
                public TableCell<RentItem, String> call(TableColumn<RentItem, String> param) {
                    return new TableCell<>() {
                        final Button btn = new Button("Decline");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                RentItem book = getTableView().getItems().get(getIndex());

                                btn.setOnAction(actionEvent -> {

                                    try {

                                        rentDB.deleteBook(book.getTitle(), book.getAuthor(), book.getClient());
                                        showMessage.setText("The request from " + book.getClient() + " has been declined!");
                                        table.setItems(rentDB.getBooks());

                                    }catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                });
                                setGraphic(btn);
                                setText(null);
                            }
                        }
                    };
                }
            };
            colDecline.setCellFactory(cellFactory2);
            table.setItems(rentDB.getBooks());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.loose.fis.sre.exceptions.NoBookFound;
import org.loose.fis.sre.model.Books;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.stageOptimise;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LibrarianController implements Initializable {

    @FXML
    private Button addBook;

    @FXML
    private TableColumn<Books, String> colAuthor;

    @FXML
    private TableColumn<Books, Boolean> colAvailability;

    @FXML
    private TableColumn<Books, String> colEdit;

    @FXML
    private TableColumn<Books, String> colTitle;

    @FXML
    public Button logout;

    @FXML
    private TableView<Books> table;

    @FXML
    private Button viewHistory;

    @FXML
    private Button viewRequests;

    @FXML
    public void addBook(javafx.event.ActionEvent actionEvent) throws IOException {
        stageOptimise.switchToStage("addBook.fxml", "Add Book", actionEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            table.setItems(null);
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            colAvailability.setCellValueFactory(new PropertyValueFactory<>("availability"));
            colEdit.setCellValueFactory(new PropertyValueFactory<>(""));

            Callback<TableColumn<Books, String>, TableCell<Books, String>> cellFactory
                    = new Callback<>() {
                @Override
                public TableCell<Books, String> call(TableColumn<Books, String> param) {
                    return new TableCell<>() {
                        final Button btn = new Button("Edit");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                Books book = getTableView().getItems().get(getIndex());

                                btn.setOnAction(actionEvent -> {
                                    try {
                                        stageOptimise.switchToStageWithPopulateTitleAuthor("editBook.fxml", "Edit Book", book.getTitle(), book.getAuthor(), false, "edit", actionEvent);
                                    } catch (IOException | SQLException | NoBookFound e) {
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
            colEdit.setCellFactory(cellFactory);

            table.setItems(bookDB.getBooks());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout (javafx.event.ActionEvent actionEvent) throws IOException {
        stageOptimise.switchToStage("login.fxml", "Log In", actionEvent);
    }

    @FXML
    void viewHistory(ActionEvent event) {

    }

    @FXML
    void viewRequests(ActionEvent event) {

    }

}

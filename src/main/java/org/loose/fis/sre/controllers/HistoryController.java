package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.loose.fis.sre.model.Books;
import org.loose.fis.sre.model.HistoryBook;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.historyDB;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.SQLException;

public class HistoryController {
    @FXML
    private TableColumn<HistoryBook, String> colAuthor;

    @FXML
    private TableColumn<HistoryBook, Integer> colBuy;

    @FXML
    private TableColumn<HistoryBook, Integer> colPeriod;

    @FXML
    private TableColumn<HistoryBook, Integer> colPrice;

    @FXML
    private TableColumn<HistoryBook, Integer> colRent;

    @FXML
    private TableColumn<HistoryBook, String> colTitle;

    @FXML
    private TableView<HistoryBook> table;

    public void initialize() {
        try {
            table.setItems(null);
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            colBuy.setCellValueFactory(new PropertyValueFactory<>("bought"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colRent.setCellValueFactory(new PropertyValueFactory<>("rented"));
            colPeriod.setCellValueFactory(new PropertyValueFactory<>("period"));

            table.setItems(historyDB.getHistoryItems());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void backButton(javafx.event.ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("customer.fxml"));
        Pane root = fxmlLoader.load();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        stage.setTitle("Customer View");
        stage.setScene(new Scene(root));
        stage.show();
    }
}

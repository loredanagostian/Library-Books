package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.loose.fis.sre.model.HistoryBook;
import org.loose.fis.sre.services.historyDB;
import org.loose.fis.sre.services.stageOptimise;

import java.io.IOException;
import java.sql.SQLException;

public class HistoryLibrarianController {

    @FXML
    private TableColumn<HistoryBook, String> colBorrowedSold;

    @FXML
    private Button backButton;

    @FXML
    private TableColumn<HistoryBook, String> colAuthor;

    @FXML
    private TableColumn<HistoryBook, String> colClient;

    @FXML
    private TableColumn<HistoryBook, Integer> colPeriod;

    @FXML
    private TableColumn<HistoryBook, String> colTitle;

    @FXML
    private TableView<HistoryBook> table;


    @FXML
    public void backButton(javafx.event.ActionEvent actionEvent) throws IOException {
        stageOptimise.switchToStage("librarian.fxml", "Library", actionEvent);
    }

    public void initialize() {
        try {
            table.setItems(null);
            colClient.setCellValueFactory(new PropertyValueFactory<>("client"));
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            colBorrowedSold.setCellValueFactory(new PropertyValueFactory<>("borrowedSold"));
            colPeriod.setCellValueFactory(new PropertyValueFactory<>("period"));

            table.setItems(historyDB.getHistoryItemsLibrarian());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

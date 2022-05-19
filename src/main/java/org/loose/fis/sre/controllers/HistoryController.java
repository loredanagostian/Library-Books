package org.loose.fis.sre.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.loose.fis.sre.model.HistoryBook;
import org.loose.fis.sre.services.historyDB;
import org.loose.fis.sre.services.stageOptimise;

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

    @FXML
    private Label username;

    public void populateWindow(String user){
        username.setText(user);
    }

    public void initialize2() {
        try {
            table.setItems(null);
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            colBuy.setCellValueFactory(new PropertyValueFactory<>("bought"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colRent.setCellValueFactory(new PropertyValueFactory<>("rented"));
            colPeriod.setCellValueFactory(new PropertyValueFactory<>("period"));

            table.setItems(historyDB.getHistoryItems(username.getText()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}

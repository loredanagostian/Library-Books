package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.loose.fis.sre.model.Books;
import org.loose.fis.sre.model.CartItems;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.cartDB;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    public Button backButton;
    @FXML
    private TableColumn<CartItems, String> colButton;

    @FXML
    private Button buyButton;

    @FXML
    private TableColumn<CartItems, String> colAuthor;

    @FXML
    private TableColumn<CartItems, Integer> colPrice;

    @FXML
    private TableColumn<CartItems, String> colTitle;

    @FXML
    private Label showTotalPrice;

    @FXML
    private TableView<CartItems> table;

    @FXML
    public void buyButton(ActionEvent actionEvent) {

    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("customer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            table.setItems(null);
            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
            colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colButton.setCellValueFactory(new PropertyValueFactory<>(""));

            Callback<TableColumn<CartItems, String>, TableCell<CartItems, String>> cellFactory
                    = new Callback<>() {
                @Override
                public TableCell<CartItems, String> call(TableColumn<CartItems, String> param) {
                    return new TableCell<>() {
                        final Button btn = new Button("Delete");

                        @Override
                        public void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);

                            if (empty) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                CartItems itemDeleted = getTableView().getItems().get(getIndex());

                                btn.setOnAction(actionEvent -> {
                                    try {
                                        cartDB.deleteItem(itemDeleted.getTitle(), itemDeleted.getAuthor());
                                        table.setItems(cartDB.getCartItems());
                                    } catch (SQLException e) {
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
            colButton.setCellFactory(cellFactory);

            table.setItems(cartDB.getCartItems());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

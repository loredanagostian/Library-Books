package org.loose.fis.sre.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.loose.fis.sre.exceptions.BookHas0Stock;
import org.loose.fis.sre.model.CartItems;
import org.loose.fis.sre.services.cartDB;
import org.loose.fis.sre.services.stageOptimise;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    public Button backButton;
    @FXML
    private TableColumn<CartItems, String> colButton;

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
    public void buyButton() {
        try {
            for(CartItems item : cartDB.getCartItems())
                cartDB.buyItem(item.getTitle(), item.getAuthor(), item.getPrice());

            table.setItems(null);
            showTotalPrice.setText("Your order has been \n finished successfully!");
        } catch (BookHas0Stock | SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
        stageOptimise.switchToStage("customer.fxml", "Customer View", actionEvent);
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

                                        Integer total = 0;
                                        for(CartItems cart_item : cartDB.getCartItems())
                                            total += cart_item.getPrice();

                                        showTotalPrice.setText(total.toString());
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

            Integer total = 0;
            for(CartItems cart_item : cartDB.getCartItems())
                total += cart_item.getPrice();

            showTotalPrice.setText(total.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

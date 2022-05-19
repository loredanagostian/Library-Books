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
import org.loose.fis.sre.Main;
import org.loose.fis.sre.exceptions.BookHas0Stock;
import org.loose.fis.sre.exceptions.EmptyCart;
import org.loose.fis.sre.model.CartItems;
import org.loose.fis.sre.services.cartDB;
import org.loose.fis.sre.services.stageOptimise;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CartController {
    @FXML
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
    private Label username;

    @FXML
    private TableView<CartItems> table;

    public void populateWindow(String user) {
        username.setText(user);
    }

    @FXML
    public void buyButton() {
        try {
            for(CartItems item : cartDB.getCartItems(username.getText()))
                cartDB.buyItem(username.getText(), item.getTitle(), item.getAuthor(), item.getPrice());

            table.setItems(null);
            showTotalPrice.setText("Your order has been \n finished successfully!");
        } catch (BookHas0Stock | SQLException e) {
            e.printStackTrace();
        } catch (EmptyCart e) {
            showTotalPrice.setText(e.getMessage());
        }
    }

    @FXML
    public void backButton(ActionEvent actionEvent) throws IOException {
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

    @FXML
    public void initialize2() {
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
                                        cartDB.deleteItem(itemDeleted.getTitle(), itemDeleted.getAuthor(), username.getText());
                                        table.setItems(cartDB.getCartItems(username.getText()));

                                        Integer total = 0;
                                        for(CartItems cart_item : cartDB.getCartItems(username.getText()))
                                            total += cart_item.getPrice();

                                        showTotalPrice.setText(total.toString());
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    } catch (EmptyCart e) {
                                        showTotalPrice.setText(e.getMessage());
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

            table.setItems(cartDB.getCartItems(username.getText()));

            Integer total = 0;
            for(CartItems cart_item : cartDB.getCartItems(username.getText()))
                total += cart_item.getPrice();

            showTotalPrice.setText(total.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (EmptyCart e) {
            showTotalPrice.setText(e.getMessage());
        }
    }
}

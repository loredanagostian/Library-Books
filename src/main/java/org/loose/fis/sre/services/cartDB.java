package org.loose.fis.sre.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.CartItemAlreadyExists;
import org.loose.fis.sre.model.CartItems;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class cartDB {
    static PreparedStatement preparedStatement = null;

    public static void addCartItem(String title, String author, Integer price, String user, String tableName) throws SQLException, CartItemAlreadyExists {
        String sql = "SELECT * FROM " + tableName + " WHERE title = ? AND author = ? AND username_client = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        preparedStatement.setString(3, user);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next())
            throw new CartItemAlreadyExists(title, author);

        sql = "INSERT INTO " + tableName + " (idcart, title, author, price, username_client) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, UUID.randomUUID().toString());
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, author);
        preparedStatement.setInt(4, price);
        preparedStatement.setString(5, user);

        preparedStatement.executeUpdate();
    }

    public static ObservableList<CartItems> getCartItems (String user, String tableName) throws SQLException {
        ObservableList<CartItems> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + tableName + " WHERE username_client = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
        preparedStatement.setString(1, user);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            CartItems items = new CartItems();
            items.setTitle(resultSet.getString("title"));
            items.setAuthor(resultSet.getString("author"));
            items.setPrice(resultSet.getInt("price"));
            items.setClient(resultSet.getString("username_client"));

            list.add(items);
        }

        return list;
    }

    public static void deleteItem (String title, String author, String user) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE title = ? AND author = ? AND username_client = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        preparedStatement.setString(3, user);

        preparedStatement.executeUpdate();
    }

    public static void buyItem (String user, String titleBook, String authorBook, Integer priceBook) throws SQLException {
        String sql = "SELECT * FROM books WHERE title = ? AND author = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
        preparedStatement.setString(1, titleBook);
        preparedStatement.setString(2, authorBook);

        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next())
                sql = "INSERT INTO history_customer (id, title, author, bought, rented, price, period, username_client) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

                preparedStatement.setString(1, UUID.randomUUID().toString());
                preparedStatement.setString(2, titleBook);
                preparedStatement.setString(3, authorBook);
                preparedStatement.setInt(4, 1);
                preparedStatement.setInt(5, 0);
                preparedStatement.setInt(6, priceBook);
                preparedStatement.setInt(7, 0);
                preparedStatement.setString(8, user);

                preparedStatement.executeUpdate();

                int stockBook = resultSet.getInt("stock");

                sql = "UPDATE books SET stock = ? WHERE title = ? AND author = ?";
                preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

                preparedStatement.setInt(1, stockBook - 1);
                preparedStatement.setString(2, titleBook);
                preparedStatement.setString(3, authorBook);

                preparedStatement.executeUpdate();

                deleteItem(titleBook, authorBook, user);

    }
}

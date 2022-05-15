package org.loose.fis.sre.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.BookHas0Stock;
import org.loose.fis.sre.exceptions.CartItemAlreadyExists;
import org.loose.fis.sre.exceptions.EmptyCart;
import org.loose.fis.sre.model.CartItems;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class cartDB {
    static PreparedStatement preparedStatement = null;

    public static void addCartItem(String title, String author, Integer price) throws SQLException, CartItemAlreadyExists, BookHas0Stock {
        String sql = "SELECT * FROM cart_items WHERE title = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, title);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next())
            throw new CartItemAlreadyExists(title);

        sql = "INSERT INTO cart_items (title, author, price) VALUES (?, ?, ?)";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        preparedStatement.setInt(3, price);

        preparedStatement.executeUpdate();
    }

    public static ObservableList<CartItems> getCartItems () throws SQLException, EmptyCart {
        ObservableList<CartItems> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM cart_items";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            CartItems items = new CartItems();
            items.setTitle(resultSet.getString("title"));
            items.setAuthor(resultSet.getString("author"));
            items.setPrice(resultSet.getInt("price"));

            list.add(items);
        }

        if (list.isEmpty())
            throw new EmptyCart();

        return list;
    }

    public static void deleteItem (String title, String author) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE title = ? AND author = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);

        preparedStatement.executeUpdate();
    }

    public static void buyItem (String titleBook, String authorBook, Integer priceBook) throws SQLException, BookHas0Stock {
        String sql = "SELECT * FROM books WHERE title = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
        preparedStatement.setString(1, titleBook);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next())
            if(resultSet.getInt("stock") > 0){
                sql = "INSERT INTO history_customer (id, title, author, bought, rented, price, period) VALUES (?, ?, ?, ?, ?, ?, ?)";
                preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

                preparedStatement.setString(1, UUID.randomUUID().toString());
                preparedStatement.setString(2, titleBook);
                preparedStatement.setString(3, authorBook);
                preparedStatement.setInt(4, 1);
                preparedStatement.setInt(5, 0);
                preparedStatement.setInt(6, priceBook);
                preparedStatement.setInt(7, 0);

                preparedStatement.executeUpdate();

                int stockBook = resultSet.getInt("stock");

                sql = "UPDATE books SET stock = ? WHERE title = ? AND author = ?";
                preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

                preparedStatement.setInt(1, stockBook - 1);
                preparedStatement.setString(2, titleBook);
                preparedStatement.setString(3, authorBook);

                preparedStatement.executeUpdate();

                deleteItem(titleBook, authorBook);
            }
            else
                throw new BookHas0Stock(titleBook, authorBook);
    }
}

package org.loose.fis.sre.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.CartItemAlreadyExists;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.CartItems;

import java.sql.*;

public class cartDB {
    static PreparedStatement preparedStatement = null;
    static dbConnection connectNow = new dbConnection();
    static Connection connection = connectNow.getConnection();

    static String connectQuery = "SELECT title FROM books";
    static Statement statement;

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static ResultSet queryOutput;
    static ResultSet resultSet;

    static {
        try {
            queryOutput = statement.executeQuery(connectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCartItem(String title, String author, Integer price) throws SQLException, CartItemAlreadyExists {
        String sql = "SELECT * FROM cart_items WHERE title = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, title);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next())
            throw new CartItemAlreadyExists(title);

        sql = "INSERT INTO cart_items (title, author, price) VALUES (?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        preparedStatement.setInt(3, price);

        preparedStatement.executeUpdate();
    }

    public static ObservableList<CartItems> getCartItems () throws SQLException {
        ObservableList<CartItems> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM cart_items";
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            CartItems items = new CartItems();
            items.setTitle(resultSet.getString("title"));
            items.setAuthor(resultSet.getString("author"));
            items.setPrice(resultSet.getInt("price"));

            list.add(items);
        }

        return list;
    }

    public static void deleteItem (String title, String author) throws SQLException {
        String sql = "DELETE FROM cart_items WHERE title = ? AND author = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);

        preparedStatement.executeUpdate();
    }
}

package org.loose.fis.sre.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.model.HistoryBook;

import java.sql.*;
import java.util.UUID;

public class historyDB {
    static PreparedStatement preparedStatement = null;

    public static ObservableList<HistoryBook> getHistoryItems (String user) throws SQLException {
        ObservableList<HistoryBook> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM history_customer WHERE username_client = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
        preparedStatement.setString(1, user);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            HistoryBook items = new HistoryBook();
            items.setTitle(resultSet.getString("title"));
            items.setAuthor(resultSet.getString("author"));
            items.setPrice(resultSet.getInt("price"));
            items.setBought(resultSet.getInt("bought"));
            items.setRented(resultSet.getInt("rented"));
            items.setPeriod(resultSet.getInt("period"));
            items.setClient(resultSet.getString("username_client"));

            list.add(items);
        }

        return list;
    }

    public static void insertRentedBook (String title, String author, Integer period, String user) throws SQLException {
        String sql = "INSERT INTO history_customer (id, title, author, bought, rented, price, period, username_client) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, UUID.randomUUID().toString());
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, author);
        preparedStatement.setInt(4, 0);
        preparedStatement.setInt(5, 1);
        preparedStatement.setInt(6, 0);
        preparedStatement.setInt(7, period);
        preparedStatement.setString(8, user);

        preparedStatement.executeUpdate();
    }
}

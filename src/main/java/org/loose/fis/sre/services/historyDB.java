package org.loose.fis.sre.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.model.HistoryBook;

import java.sql.*;

public class historyDB {
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

    public static ObservableList<HistoryBook> getHistoryItems () throws SQLException {
        ObservableList<HistoryBook> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM history_customer";
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            HistoryBook items = new HistoryBook();
            items.setTitle(resultSet.getString("title"));
            items.setAuthor(resultSet.getString("author"));
            items.setPrice(resultSet.getInt("price"));
            items.setBought(resultSet.getInt("bought"));
            items.setRented(resultSet.getInt("rented"));
            items.setPeriod(resultSet.getInt("period"));

            list.add(items);
        }

        return list;
    }
}

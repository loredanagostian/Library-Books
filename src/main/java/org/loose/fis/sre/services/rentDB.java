package org.loose.fis.sre.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.BookAlreadyRequested;
import org.loose.fis.sre.model.Books;
import org.loose.fis.sre.model.RentItem;

import java.sql.*;
import java.util.UUID;

import static org.loose.fis.sre.model.Books.setBook;
import static org.loose.fis.sre.model.RentItem.setRentBook;

public class rentDB {
    static PreparedStatement preparedStatement = null;

    public static void rentBook (String client, String title, String author, Integer period) throws SQLException, BookAlreadyRequested {
        String sql = "SELECT * FROM reguests WHERE title = ? AND author = ? AND username_client = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        preparedStatement.setString(3, client);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next())
            throw new BookAlreadyRequested(title);

        sql = "INSERT INTO reguests (idreguests, username_client, title, author, period_of_renting) VALUES  (?, ?, ?, ?, ?)";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, UUID.randomUUID().toString());
        preparedStatement.setString(2, client);
        preparedStatement.setString(3, title);
        preparedStatement.setString(4, author);
        preparedStatement.setInt(5, period);

        preparedStatement.executeUpdate();
    }

    public static ObservableList<RentItem> getBooks() throws SQLException {
        ObservableList<RentItem> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM reguests";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            RentItem book = new RentItem();
            setRentBook (book, resultSet);
            list.add(book);
        }

        return list;
    }

    public static void deleteBook (String title, String author, String user) throws SQLException {
        String sql = "DELETE FROM reguests WHERE title = ? AND author = ? AND username_client = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        preparedStatement.setString(3, user);

        preparedStatement.executeUpdate();
    }
}

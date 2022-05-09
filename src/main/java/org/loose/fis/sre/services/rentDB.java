package org.loose.fis.sre.services;

import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.BookAlreadyRequested;

import java.sql.*;
import java.util.UUID;

public class rentDB {
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

    public static void rentBook (String title, String author, Integer period) throws SQLException, BookAlreadyRequested {
        String sql = "SELECT * FROM reguests WHERE title = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, title);
        resultSet = preparedStatement.executeQuery();

        if(resultSet.next())
            throw new BookAlreadyRequested(title);

        sql = "INSERT INTO reguests (idreguests, title, author, period_of_renting) VALUES  (?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, UUID.randomUUID().toString());
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, author);
        preparedStatement.setInt(4, period);

        preparedStatement.executeUpdate();
    }
}

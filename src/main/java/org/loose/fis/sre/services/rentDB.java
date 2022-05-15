package org.loose.fis.sre.services;

import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.BookAlreadyRequested;

import java.sql.*;
import java.util.UUID;

public class rentDB {
    static PreparedStatement preparedStatement = null;

    public static void rentBook (String title, String author, Integer period) throws SQLException, BookAlreadyRequested {
        String sql = "SELECT * FROM reguests WHERE title = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
        preparedStatement.setString(1, title);
        ResultSet resultSet = preparedStatement.executeQuery();

        if(resultSet.next())
            throw new BookAlreadyRequested(title);

        sql = "INSERT INTO reguests (idreguests, title, author, period_of_renting) VALUES  (?, ?, ?, ?)";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, UUID.randomUUID().toString());
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, author);
        preparedStatement.setInt(4, period);

        preparedStatement.executeUpdate();
    }
}

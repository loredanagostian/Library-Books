package org.loose.fis.sre.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.InvalidPassword;
import org.loose.fis.sre.exceptions.NoBookFound;
import org.loose.fis.sre.exceptions.UsernameNotFound;
import org.loose.fis.sre.model.Books;

import java.sql.*;
import java.util.NoSuchElementException;

public class bookDB {
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

    public static ObservableList<Books> getBooks () throws SQLException {
        ObservableList<Books> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM books";
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Books book = new Books();
            book.setId(resultSet.getInt("id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setForBuy(resultSet.getBoolean("for_buy"));
            book.setStock(resultSet.getInt("stock"));
            book.setForRent(resultSet.getBoolean("for_rent"));
            book.setAvailability(resultSet.getBoolean("availability"));

            list.add(book);
        }

        return list;
    }

    public static ObservableList<Books> getSearchedBooks (String searched) throws SQLException, NoBookFound {
        ObservableList<Books> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM books WHERE books.title LIKE ? ";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + searched + "%");
        resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            Books book = new Books();
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));

            list.add(book);
        }

        if(list.isEmpty())
            throw new NoBookFound(searched);

        return list;
    }

    public static Books searchBook (String title, String author) throws SQLException, NoBookFound {
        String sql = "SELECT * FROM books WHERE title = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, title);
        resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new NoBookFound(title);

        sql = "SELECT * FROM books WHERE title = ? AND author = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new NoSuchElementException("Nu s-a gasit acesta combinatie de titlu-autor.");

        Books book = new Books();
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setPrice(resultSet.getInt("price"));

        return book;
    }
}
package org.loose.fis.sre.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.*;
import org.loose.fis.sre.model.Books;

import java.sql.*;
import java.util.NoSuchElementException;
import java.util.UUID;

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

    public static ObservableList<Books> getBooks() throws SQLException {
        ObservableList<Books> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM books";
        preparedStatement = connection.prepareStatement(sql);
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Books book = new Books();
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setPrice(resultSet.getInt("price"));

            if(resultSet.getInt("availability") == 1)
                book.setAvailability("Available");
            else
                book.setAvailability("NOT available");

            book.setStock(resultSet.getInt("stock"));
            book.setForRent(resultSet.getInt("for_rent"));
            book.setForBuy(resultSet.getInt("for_buy"));

            list.add(book);
        }

        return list;
    }

    public static ObservableList<Books> getSearchedBooks(String searched) throws SQLException, NoBookFound {
        ObservableList<Books> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM books WHERE books.title LIKE ? ";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "%" + searched + "%");
        resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Books book = new Books();
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setPrice(resultSet.getInt("price"));

            if(resultSet.getInt("availability") == 1)
                book.setAvailability("Available");
            else
                book.setAvailability("NOT available");

            book.setStock(resultSet.getInt("stock"));
            book.setForRent(resultSet.getInt("for_rent"));
            book.setForBuy(resultSet.getInt("for_buy"));

            list.add(book);
        }

        if (list.isEmpty())
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

        if(resultSet.getInt("availability") == 1)
            book.setAvailability("Available");
        else
            book.setAvailability("NOT available");

        book.setStock(resultSet.getInt("stock"));
        book.setForRent(resultSet.getInt("for_rent"));
        book.setForBuy(resultSet.getInt("for_buy"));

        return book;
    }

    public static void editBook(String bookTitle, String title, String author, String description, String price, String rentBuy, String stock, String availability) throws SQLException {
        String sql = "SELECT * FROM books WHERE title = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, bookTitle);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            if (title.equals("")) title = resultSet.getString("title");
            if (author.equals("")) author = resultSet.getString("author");
            if (description.equals("")) description = resultSet.getString("description");
            if (price.equals("")) price = String.valueOf(resultSet.getInt("price"));
            if (rentBuy.equals(""))
                if (resultSet.getBoolean("for_buy")) rentBuy = "Buy";
                else if (resultSet.getBoolean("for_rent")) rentBuy = "Rent";
                else if (resultSet.getBoolean("for_buy") && resultSet.getBoolean("for_rent")) rentBuy = "Rent and Buy";
            if (stock.equals("")) stock = String.valueOf(resultSet.getInt("stock"));
            if (availability.equals("")) if (resultSet.getBoolean("availability")) availability = "Available";
        }

        int forRent = 0, forBuy = 0;
        if (rentBuy.equals("Buy")) forBuy = 1;
        else if (rentBuy.equals("Rent")) forRent = 1;
        else {
            forBuy = 1;
            forRent = 1;
        }

        int available = 0;
        if (availability.equals("Available")) available = 1;

        String sql2 = "UPDATE books SET title=?, author=?, for_buy=?, stock=?, for_rent=?, availability=?, description=?, price=? WHERE title = ? ";
        preparedStatement = connection.prepareStatement(sql2);
        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        preparedStatement.setInt(3, forBuy);
        preparedStatement.setInt(4, Integer.parseInt(stock));
        preparedStatement.setInt(5, forRent);
        preparedStatement.setInt(6, available);
        preparedStatement.setString(7, description);
        preparedStatement.setInt(8, Integer.parseInt(price));
        preparedStatement.setString(9, bookTitle);
        int i =  preparedStatement.executeUpdate();

    }

    public static void insertBook(String title, String author, String description, String price, String rentBuy, String stock, String availability) throws BookAlreadyExistsException, SQLException {
        String sql = "SELECT * FROM books WHERE title = ? AND author = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, title);
        preparedStatement.setString(2, author);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next())
            throw new BookAlreadyExistsException(title, author);

        sql = "INSERT INTO books (id, title, author, for_buy, stock, for_rent, availability, description, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);

        int forRent = 0, forBuy = 0;
        if (rentBuy.equals("Buy")) forBuy = 1;
        else if (rentBuy.equals("Rent")) forRent = 1;
        else {
            forBuy = 1;
            forRent = 1;
        }
        int available = 0;
        if (availability.equals("Available")) available = 1;

        preparedStatement.setString(1, UUID.randomUUID().toString());
        preparedStatement.setString(2, title);
        preparedStatement.setString(3, author);
        preparedStatement.setInt(4, forBuy);
        preparedStatement.setInt(5, Integer.parseInt(stock));
        preparedStatement.setInt(6, forRent);
        preparedStatement.setInt(7, available);
        preparedStatement.setString(8, description);
        preparedStatement.setInt(9, Integer.parseInt(price));

        preparedStatement.executeUpdate();

    }
}

package org.loose.fis.sre.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.dbConnectionTest;
import org.loose.fis.sre.exceptions.BookAlreadyExistsException;
import org.loose.fis.sre.exceptions.NoBookFound;
import org.loose.fis.sre.model.Books;
import org.testfx.framework.junit5.ApplicationExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ApplicationExtension.class)
public class bookDBTest {
    public static final String TITLE = "titlu - test";
    public static final String AUTHOR = "author - test";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "25";
    public static final String RENTBUY = "Rent";
    public static final String STOCK = "10";
    public static final String AVAILABILITY = "Available";

    @BeforeEach
    void connectDB() throws SQLException {
        Connection con = dbConnectionTest.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM books_test");
        preparedStatement.executeUpdate();
    }

    @Test
    @DisplayName("Add book - successfully")
    void addBookTest() throws SQLException, BookAlreadyExistsException {
        bookDB.insertBook(TITLE, AUTHOR, DESCRIPTION, PRICE, RENTBUY, STOCK, AVAILABILITY, "books_test");
        Assertions.assertEquals(bookDB.getBooks("books_test").size(), 1);

        Books book = bookDB.getBooks("books_test").get(0);
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(TITLE);
        assertThat(book.getAuthor()).isEqualTo(AUTHOR);
        assertThat(book.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(book.getPrice().toString()).isEqualTo(PRICE);
        assertThat(book.getForRent()).isEqualTo(1);
        assertThat(book.getStock().toString()).isEqualTo(STOCK);
        assertThat(book.getAvailability()).isEqualTo(AVAILABILITY);
    }

    @Test
    @DisplayName("Book already exists")
    void addBookTest2() {
        assertThrows(BookAlreadyExistsException.class, () ->
                {
                    addBookTest();
                    bookDB.insertBook(TITLE, AUTHOR, DESCRIPTION, PRICE, RENTBUY, STOCK, AVAILABILITY, "books_test");
                }
        );
    }

    @Test
    @DisplayName("Search book - successfully")
    void searchBookTest() throws SQLException, NoBookFound, BookAlreadyExistsException {
        addBookTest();
        Books book = bookDB.searchBook(TITLE, AUTHOR, "books_test");
        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(TITLE);
        assertThat(book.getAuthor()).isEqualTo(AUTHOR);
    }

    @Test
    @DisplayName("Book not found")
    void searchBookTest2() {
        assertThrows(NoBookFound.class, () ->
        {
            addBookTest();
            bookDB.searchBook("altTitlu", "altAutor", "books_test");
        });
    }
}

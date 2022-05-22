package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.dbConnectionTest;
import org.loose.fis.sre.exceptions.BookAlreadyExistsException;
import org.loose.fis.sre.exceptions.NoBookFound;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.stageOptimise;
import org.loose.fis.sre.services.userDB;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class searchBookTest {

    public static final String TITLE = "titlu - test";
    public static final String AUTHOR = "author - test";


    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(stageOptimise.class.getClassLoader().getResource("customer.fxml"));
        Pane root = fxmlLoader.load();

        stage.setTitle("Customer View");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void connectDB() throws SQLException, BookAlreadyExistsException {
        Connection con = dbConnectionTest.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM books_test");
        preparedStatement.executeUpdate();
        bookDB.insertBook(TITLE, AUTHOR, "description", "15", "Buy", "50", "", "books_test");
    }

    @Test
    @DisplayName("Book successfully found")
    void testSearchBook1(FxRobot robot) throws SQLException, NoBookFound {

        String TITLE2 = "test";
        robot.clickOn("#searchField");
        robot.write(TITLE2);
        robot.clickOn("#searchButton");

        assertThat(bookDB.getSearchedBooks(TITLE2, "books_test").get(0).getTitle().contains(TITLE2));
    }

    @Test
    @DisplayName("Book unsuccessfully found - Empty search field")
    void testSearchBook2(FxRobot robot) {

        robot.clickOn("#searchField");
        robot.write("");
        robot.clickOn("#searchButton");

        Assertions.assertThat(robot.lookup("#showMessage").queryLabeled()).hasText(
                "You have to type something.");
    }

    @Test
    @DisplayName("Book unsuccessfully found - no book found with the specific title")
    void testSearchBook3(FxRobot robot) {

        robot.clickOn("#searchField");
        robot.write("testing");
        robot.clickOn("#searchButton");

        Assertions.assertThat(robot.lookup("#showMessage").queryLabeled()).hasText(
                String.format("No book found with this title!")
        );
    }
}

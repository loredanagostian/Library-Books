package org.loose.fis.sre.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.dbConnectionTest;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.stageOptimise;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testfx.assertions.api.Assertions.assertThat;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;

@ExtendWith(ApplicationExtension.class)
public class addBookTest {

    public static final String TITLE = "titlu - test";
    public static final String AUTHOR = "author - test";
    public static final String DESCRIPTION = "description";
    public static final String PRICE = "25";
    public static final String RENTBUY = "Rent and Buy";
    public static final String STOCK = "10";
    public static final String AVAILABILITY = "Available";

    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(stageOptimise.class.getClassLoader().getResource("addBook.fxml"));
        Pane root = fxmlLoader.load();

        stage.setTitle("Add Book");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void connectDB() throws SQLException {
        Connection con = dbConnectionTest.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM books_test");
        preparedStatement.executeUpdate();
    }

    @Test
    @DisplayName("Book added successfully")
    void testAddBook1(FxRobot robot) throws SQLException {

        robot.clickOn("#titleField");
        robot.write(TITLE);
        robot.clickOn("#authorField");
        robot.write(AUTHOR);
        robot.clickOn("#descriptionField");
        robot.write(DESCRIPTION);
        robot.clickOn("#priceField");
        robot.write(PRICE);
        robot.clickOn("#rentBuyBox").clickOn(RENTBUY);
        robot.clickOn("#stockField");
        robot.write(STOCK);
        robot.clickOn("#availabilityBox").clickOn(AVAILABILITY);

        robot.clickOn("#addButton");

        Assertions.assertEquals(bookDB.getBooks("books_test").size(), 1);
    }

    @Test
    @DisplayName("Book for rent unsuccessfully added - must fill in all required details")
    void testAddBook2(FxRobot robot) {

        robot.clickOn("#titleField");
        robot.write(TITLE);
        robot.clickOn("#authorField");
        robot.write(AUTHOR);
        robot.clickOn("#descriptionField");
        robot.write(DESCRIPTION);
        robot.clickOn("#priceField");
        robot.write("");
        robot.clickOn("#rentBuyBox").clickOn("Rent");
        robot.clickOn("#stockField");
        robot.write("");
        robot.clickOn("#availabilityBox").clickOn("");

        robot.clickOn("#addButton");

        assertThat(robot.lookup("#showMessage").queryLabeled()).hasText("You must fill in all the required details for rent!");
    }

    @Test
    @DisplayName("Book for buy unsuccessfully added - must fill in all required details")
    void testAddBook3(FxRobot robot) {

        robot.clickOn("#titleField");
        robot.write(TITLE);
        robot.clickOn("#authorField");
        robot.write(AUTHOR);
        robot.clickOn("#descriptionField");
        robot.write(DESCRIPTION);
        robot.clickOn("#priceField");
        robot.write("");
        robot.clickOn("#rentBuyBox").clickOn("Buy");
        robot.clickOn("#stockField");
        robot.write(STOCK);
        robot.clickOn("#availabilityBox").clickOn("");

        robot.clickOn("#addButton");

        assertThat(robot.lookup("#showMessage").queryLabeled()).hasText("You must fill in all the required details for buy!");
    }

    @Test
    @DisplayName("Book for rent and buy unsuccessfully added - must fill in all details")
    void testAddBook4(FxRobot robot) {

        robot.clickOn("#titleField");
        robot.write(TITLE);
        robot.clickOn("#authorField");
        robot.write(AUTHOR);
        robot.clickOn("#descriptionField");
        robot.write(DESCRIPTION);
        robot.clickOn("#priceField");
        robot.write(PRICE);
        robot.clickOn("#rentBuyBox").clickOn(RENTBUY);
        robot.clickOn("#stockField");
        robot.write("");
        robot.clickOn("#availabilityBox").clickOn(AVAILABILITY);

        robot.clickOn("#addButton");

        assertThat(robot.lookup("#showMessage").queryLabeled()).hasText("You must fill in all the required details!");
    }

    @Test
    @DisplayName("Unsuccessfully add book - the book already exists")
    void testAddBook5(FxRobot robot) throws SQLException {
        testAddBook1(robot);
        robot.clickOn("OK");
        robot.clickOn("#addBook");

        robot.clickOn("#titleField");
        robot.write(TITLE);
        robot.clickOn("#authorField");
        robot.write(AUTHOR);
        robot.clickOn("#descriptionField");
        robot.write(DESCRIPTION);
        robot.clickOn("#priceField");
        robot.write("");
        robot.clickOn("#rentBuyBox").clickOn("Rent");
        robot.clickOn("#stockField");
        robot.write("");
        robot.clickOn("#availabilityBox").clickOn(AVAILABILITY);

        robot.clickOn("#addButton");

        FxAssert.verifyThat("#addBookError",isEnabled());
        robot.clickOn("#addBookError");

    }

}

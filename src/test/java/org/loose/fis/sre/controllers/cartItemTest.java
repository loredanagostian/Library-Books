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
import org.loose.fis.sre.exceptions.BookAlreadyExistsException;
import org.loose.fis.sre.services.bookDB;
import org.loose.fis.sre.services.cartDB;
import org.loose.fis.sre.services.stageOptimise;
import org.loose.fis.sre.services.userDB;
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
public class cartItemTest {


    public static final String TITLE = "titlu - test";
    public static final String AUTHOR = "author - test";
    public static final String USER = "test";

    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(stageOptimise.class.getClassLoader().getResource("login.fxml"));
        Pane root = fxmlLoader.load();

        stage.setTitle("Login Page");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void connectDB() throws SQLException, BookAlreadyExistsException {
        Connection con = dbConnectionTest.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM books_test");
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatement2 = con.prepareStatement("DELETE FROM cart_test");
        preparedStatement2.executeUpdate();

        bookDB.insertBook(TITLE, AUTHOR, "description", "15", "Buy", "50", "Not available", "books_test");
    }

    @Test
    @DisplayName("Cart item successfully added")
    void testCartItem1(FxRobot robot) throws SQLException {
        robot.clickOn("#usernameTextField");
        robot.write(USER);
        robot.clickOn("#passwordPassField");
        robot.write("123");
        robot.clickOn("#connectButton");

        robot.clickOn(TITLE);
        robot.clickOn("#buyButton");

        Assertions.assertEquals(cartDB.getCartItems(USER,"cart_test").size(), 1);
    }

    @Test
    @DisplayName("Cart item successfully added")
    void testCartItem2(FxRobot robot) throws SQLException {
        testCartItem1(robot);
        robot.clickOn("Back");

        robot.clickOn(TITLE);
        robot.clickOn("#buyButton");

        FxAssert.verifyThat("#addItemError",isEnabled());
        robot.clickOn("#addItemError");
    }

}

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
import org.loose.fis.sre.services.stageOptimise;
import org.loose.fis.sre.services.userDB;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class registerTest {

    public static final String USERNAME = "test";
    public static final String PASSWORD = "123";
    public static final String NAME = "testTest";
    public static final String EMAIL = "test@test.com";

    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(stageOptimise.class.getClassLoader().getResource("register.fxml"));
        Pane root = fxmlLoader.load();

        stage.setTitle("Register");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void connectDB() throws SQLException {
        Connection con = dbConnectionTest.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM users_test");
        preparedStatement.executeUpdate();
    }

    @Test
    @DisplayName("Successfully register")
    void testRegister1(FxRobot robot) throws SQLException {
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#roleBox").clickOn("Customer");
        robot.clickOn("#nameField");
        robot.write(NAME);
        robot.clickOn("#emailField");
        robot.write(EMAIL);
        robot.clickOn("#registerButton");

        Assertions.assertEquals(userDB.getUsers("users_test").size(), 1);
    }

    @Test
    @DisplayName("Unsuccessfully register - Account already exists")
    void testRegister2 (FxRobot robot) throws SQLException {
        testRegister1(robot);

        robot.clickOn("#logoutButton");
        robot.clickOn("#registerButton");

        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#roleBox").clickOn("Customer");
        robot.clickOn("#nameField");
        robot.write(NAME);
        robot.clickOn("#emailField");
        robot.write(EMAIL);

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#showMessage").queryLabeled()).hasText(
                String.format("An account with the username %s already exists!", USERNAME)
        );
    }

    @Test
    @DisplayName("Unsuccessfully register - You must fill all fields")
    void testRegister3 (FxRobot robot) {
        robot.clickOn("#usernameField");
        robot.write("");
        robot.clickOn("#passwordField");
        robot.write("");
        robot.clickOn("#roleBox").clickOn("Librarian");
        robot.clickOn("#nameField");
        robot.write(NAME);
        robot.clickOn("#emailField");
        robot.write(EMAIL);

        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#showMessage").queryLabeled()).hasText("You must fill in all the required details!");
    }
}

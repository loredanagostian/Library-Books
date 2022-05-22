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
import org.loose.fis.sre.exceptions.InvalidPassword;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.exceptions.UsernameNotFound;
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
public class loginTest {

    public static final String USERNAME = "test";
    public static final String PASSWORD = "123";

    @Start
    void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(stageOptimise.class.getClassLoader().getResource("login.fxml"));
        Pane root = fxmlLoader.load();

        stage.setTitle("Login Page");
        stage.setScene(new Scene(root));
        stage.show();
    }

    @BeforeEach
    void connectDB() throws SQLException, UsernameAlreadyExistsException {
        Connection con = dbConnectionTest.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM users_test");
        preparedStatement.executeUpdate();
        userDB.insertUser(USERNAME, PASSWORD, "Customer", "testTest", "test@test.com", "users_test");
    }

    @Test
    @DisplayName("Login successfully")
    void testLogin1(FxRobot robot) throws UsernameNotFound, InvalidPassword, SQLException {

        robot.clickOn("#usernameTextField");
        robot.write(USERNAME);
        robot.clickOn("#passwordPassField");
        robot.write(PASSWORD);
        robot.clickOn("#connectButton");

        Assertions.assertEquals(userDB.loginUser(USERNAME, PASSWORD, "users_test"), "Customer");
    }

    @Test
    @DisplayName("Unsuccessfully login - You must type your username")
    void testLogin2(FxRobot robot) {

        robot.clickOn("#usernameTextField");
        robot.write("");
        robot.clickOn("#passwordPassField");
        robot.write(PASSWORD);
        robot.clickOn("#connectButton");

        assertThat(robot.lookup("#showMessage").queryLabeled()).hasText("Please type your username!");
    }

    @Test
    @DisplayName("Unsuccessfully login - Password cannot be empty")
    void testLogin3(FxRobot robot) {

        robot.clickOn("#usernameTextField");
        robot.write(USERNAME);
        robot.clickOn("#passwordPassField");
        robot.write("");
        robot.clickOn("#connectButton");

        assertThat(robot.lookup("#showMessage").queryLabeled()).hasText("Password cannot be empty!");
    }

    @Test
    @DisplayName("Unsuccessfully login - Invalid password")
    void testLogin4(FxRobot robot) {

        robot.clickOn("#usernameTextField");
        robot.write(USERNAME);
        robot.clickOn("#passwordPassField");
        robot.write("test");
        robot.clickOn("#connectButton");

        assertThat(robot.lookup("#showMessage").queryLabeled()).hasText(
                String.format("Password incorrect. Please fill in again.")
        );

    }

    @Test
    @DisplayName("Unsuccessfully login - Username not found")
    void testLogin5(FxRobot robot) {

        robot.clickOn("#usernameTextField");
        robot.write("admin");
        robot.clickOn("#passwordPassField");
        robot.write(PASSWORD);
        robot.clickOn("#connectButton");

        assertThat(robot.lookup("#showMessage").queryLabeled()).hasText(
                String.format("An account with the username %s does not exist! You have to register first.", "admin")
        );
    }

}

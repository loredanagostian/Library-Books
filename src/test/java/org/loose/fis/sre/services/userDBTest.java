package org.loose.fis.sre.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.dbConnectionTest;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.model.User;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
public class userDBTest {
    public static final String USERNAME = "test";
    public static final String PASSWORD = "123";
    public static final String NAME = "testTest";
    public static final String EMAIL = "test@test.com";
    public static final String ROLE = "Customer";

    @BeforeEach
    void connectDB() throws SQLException {
        Connection con = dbConnectionTest.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM users_test");
        preparedStatement.executeUpdate();
    }

    @Test
    @DisplayName("Insert user")
    void insertUserTest() throws UsernameAlreadyExistsException, SQLException {
        userDB.insertUser(USERNAME, PASSWORD, ROLE, NAME, EMAIL, "users_test");
        Assertions.assertEquals(userDB.getUsers("users_test").size(), 1);

        User user = userDB.getUsers("users_test").get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
        assertThat(user.getRole()).isEqualTo(ROLE);
        assertThat(user.getEmail()).isEqualTo(EMAIL);
        assertThat(user.getName()).isEqualTo(NAME);
    }

    @Test
    @DisplayName("User already exists")
    void insertUserTest2() {
        assertThrows(UsernameAlreadyExistsException.class, () ->
                {
                    insertUserTest();
                    insertUserTest();
                }
        );
    }
}

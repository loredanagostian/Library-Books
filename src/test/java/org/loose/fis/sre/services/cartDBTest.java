package org.loose.fis.sre.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.loose.fis.sre.dbConnectionTest;
import org.loose.fis.sre.exceptions.CartItemAlreadyExists;
import org.loose.fis.sre.model.CartItems;
import org.testfx.framework.junit5.ApplicationExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(ApplicationExtension.class)
public class cartDBTest {
    public static final String TITLE = "titlu - test";
    public static final String AUTHOR = "author - test";
    public static final Integer PRICE = 25;
    public static final String USER = "user - test";

    @BeforeEach
    void connectDB() throws SQLException {
        Connection con = dbConnectionTest.getConnection();
        PreparedStatement preparedStatement = con.prepareStatement("DELETE FROM cart_test");
        preparedStatement.executeUpdate();
    }

    @Test
    @DisplayName("Add cart item - successfully")
    void addCartItemTest() throws CartItemAlreadyExists, SQLException {
        cartDB.addCartItem(TITLE, AUTHOR, PRICE, USER, "cart_test");
        Assertions.assertEquals(cartDB.getCartItems(USER, "cart_test").size(), 1);

        CartItems item = cartDB.getCartItems(USER, "cart_test").get(0);
        assertThat(item).isNotNull();
        assertThat(item.getTitle()).isEqualTo(TITLE);
        assertThat(item.getAuthor()).isEqualTo(AUTHOR);
        assertThat(item.getPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("Cart item already exists")
    void addCartItemTest2() {
        assertThrows (CartItemAlreadyExists.class, () ->
        {
           addCartItemTest();
           cartDB.addCartItem(TITLE, AUTHOR, PRICE, USER, "cart_test");
        });
    }
}

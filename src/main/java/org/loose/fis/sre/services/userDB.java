package org.loose.fis.sre.services;

import org.dizitart.no2.objects.ObjectRepository;
import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.InvalidCredentials;
import org.loose.fis.sre.exceptions.InvalidPassword;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.exceptions.UsernameNotFound;
import org.loose.fis.sre.model.User;

import javax.xml.transform.Result;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;



public class userDB {
    PreparedStatement preparedStatement = null;
    static dbConnection connectNow = new dbConnection();
    static Connection connection = connectNow.getConnection();

    static String connectQuery = "SELECT username FROM users";
    static Statement statement;

    static {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static ResultSet queryOutput;

    static {
        try {
            queryOutput = statement.executeQuery(connectQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public userDB() throws SQLException {
    }


    public static void insertUser(String username, String password, String role, String name, String email) throws UsernameAlreadyExistsException, SQLException {
        while (queryOutput.next()){
            checkUserDoesNotAlreadyExist("username");
        }

        String sql = "INSERT INTO users (username, password, role, name, email) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, name);
            preparedStatement.setString(5, email);

            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            System.out.println(e);
        }
    }

    public static void loginUser(String username, String password) throws UsernameNotFound, InvalidPassword, SQLException, InvalidCredentials {
        String sql = "SELECT * FROM users WHERE username = ?";

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new UsernameNotFound(username);

        sql = "SELECT * FROM users WHERE username = ? and password = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new InvalidPassword(password);
    }

    public static void checkUsernameNotFound(String username) throws UsernameNotFound, SQLException {
        boolean found = false;

        while (queryOutput.next())
            if(username.equals(queryOutput.getString("username")))
                found = true;

        if(!found)
            throw new UsernameNotFound(username);
    }

    public static void checkInvalidPassword(String username, String password) throws InvalidPassword, UsernameNotFound, SQLException {
        checkUsernameNotFound(username);

        while (queryOutput.next())
            if(username.equals(queryOutput.getString("username")))
                if(!password.equals(queryOutput.getString("password")))
                    throw new InvalidPassword(password);
    }

    public static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException, SQLException {
        while (queryOutput.next()) {
            if (username.equals(queryOutput.getString("username")))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    public static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

}

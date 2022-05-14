package org.loose.fis.sre.services;

import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.InvalidPassword;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.exceptions.UsernameNotFound;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;


public class userDB {
    static PreparedStatement preparedStatement = null;
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

    public userDB() {
    }

    static ResultSet resultSet;

    public static void insertUser(String username, String password, String role, String name, String email) throws UsernameAlreadyExistsException, SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next())
            throw new UsernameAlreadyExistsException(username);

        sql = "INSERT INTO users (username, password, role, name, email) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        preparedStatement.setString(3, role);
        preparedStatement.setString(4, name);
        preparedStatement.setString(5, email);

        preparedStatement.executeUpdate();
    }

    public static String loginUser (String username, String password) throws UsernameNotFound, InvalidPassword, SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new UsernameNotFound(username);

        sql = "SELECT * FROM users WHERE username = ? AND password = ?;";

        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new InvalidPassword(password);

        return resultSet.getString("role");
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

    public static String Role(String user) throws SQLException{
        String sql = "SELECT * FROM users WHERE username = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user);
        resultSet = preparedStatement.executeQuery();

        String user_role="";
        if (resultSet.next())
            user_role = resultSet.getString("role");

        return user_role;
    }
}

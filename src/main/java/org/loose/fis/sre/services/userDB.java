package org.loose.fis.sre.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.loose.fis.sre.controllers.dbConnection;
import org.loose.fis.sre.exceptions.InvalidPassword;
import org.loose.fis.sre.exceptions.UsernameAlreadyExistsException;
import org.loose.fis.sre.exceptions.UsernameNotFound;
<<<<<<< Updated upstream
import org.loose.fis.sre.model.HistoryBook;
import org.loose.fis.sre.model.User;
=======
<<<<<<< Updated upstream
=======
import org.loose.fis.sre.model.User;
>>>>>>> Stashed changes
>>>>>>> Stashed changes

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class userDB {
    static PreparedStatement preparedStatement = null;
    
    public static void insertUser(String username, String password, String role, String name, String email, String tableName) throws UsernameAlreadyExistsException, SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE username = ?";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next())
            throw new UsernameAlreadyExistsException(username);

        sql = "INSERT INTO " + tableName + " (username, password, role, name, email) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, encodePassword(username, password));
        preparedStatement.setString(3, role);
        preparedStatement.setString(4, name);
        preparedStatement.setString(5, email);

        preparedStatement.executeUpdate();
    }

    public static String loginUser (String username, String password, String tableName) throws UsernameNotFound, InvalidPassword, SQLException {
        String sql = "SELECT * FROM " + tableName + " WHERE username = ?";

        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (!resultSet.next())
            throw new UsernameNotFound(username);

        sql = "SELECT * FROM " + tableName + " WHERE username = ? AND password = ?;";

        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
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
        return new String(hashedPassword, StandardCharsets.UTF_8);
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

    public static ObservableList<User> getUsers (String tableName) throws SQLException {
        ObservableList<User> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM " + tableName;
        preparedStatement = dbConnection.initiateConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while(resultSet.next()){
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setRole(resultSet.getString("role"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));

            list.add(user);
        }

        return list;
    }
}

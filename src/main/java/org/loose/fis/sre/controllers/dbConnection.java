package org.loose.fis.sre.controllers;

import java.sql.*;

public class dbConnection {
    public Connection dbLink;

    public Connection getConnection(){
        String dbName = "fis";
        String dbUser = "lore";
        String dbPassword = "1234";
        String url = "jdbc:mysql://localhost:3306/" + dbName;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbLink = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dbLink;
    }

    public static Connection initiateConnection() throws SQLException {
        dbConnection connectNow = new dbConnection();

        return connectNow.getConnection();
    }
}

package org.loose.fis.sre;

import java.sql.*;

public class dbConnectionTest {
    public static Connection dbLink;

    public static Connection getConnection(){
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
        dbConnectionTest connectNow = new dbConnectionTest();

        return getConnection();
    }
}

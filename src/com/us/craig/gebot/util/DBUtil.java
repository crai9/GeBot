package com.us.craig.gebot.util;

/**
 * Created by craig on 04/10/2015.
 */

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/twitch";

    //  Database credentials
    static final String USER = "java";
    static final String PASS = "java";

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(JDBC_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(
                    DB_URL, USER,PASS);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

    public static void main(String[] args){

        String query = "cannonball";

        int id = searchForItemId(query);

        System.out.println(query + "'s id is: " + id);

    }

    public static void logPreparedStatement(PreparedStatement p){
        System.out.println("[QUERY] : " + p.toString().split(": ")[1]);
    }

    public static int searchForItemId(String item){

        Connection dbConnection = null;

        PreparedStatement preparedStatement = null;

        String sql = "SELECT itemId as id FROM items WHERE itemName = ?";

        try {
            dbConnection = getDBConnection();
            preparedStatement = dbConnection.prepareStatement(sql);

            preparedStatement.setString(1, item);

            logPreparedStatement(preparedStatement);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()){
                int id = rs.getInt("id");
                return id;
            } else {
                System.err.println("no next");

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return -2;

    }
}

package org.youcode.baticuisine.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn;
    private static DBConnection instance;

    private DBConnection() {
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection establishConnection() {
        if (conn == null) {
            String jdbcUrl = "jdbc:postgresql://localhost:5432/baticuisine";
            String username = "postgres";
            String password = "password";

            try {
                Class.forName("org.postgresql.Driver");
                conn = DriverManager.getConnection(jdbcUrl, username, password);

                if (conn != null) {
                    System.out.println("Connection to PostgreSQL database established.");
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

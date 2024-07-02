package com.githab.grinevskayaab;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private String URL_DB = "jdbc:postgresql://localhost:5432/postgres";
    private String USER_DB = "admin";
    private String PASSWORD_DB = "admin";
    Connection connection;


    public Connection connectDatabase() {
        try {
            this.connection = DriverManager.getConnection(URL_DB, USER_DB, PASSWORD_DB);
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к БД");
            e.printStackTrace();
            closeConnection(this.connection);
            throw new RuntimeException(e);
        }
        return this.connection;
    }


    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

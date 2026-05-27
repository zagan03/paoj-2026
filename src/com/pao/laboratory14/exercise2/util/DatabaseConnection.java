package com.pao.laboratory14.exercise2.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Citim fisierul db.properties pus in folderul resources/
                Properties props = new Properties();
                try (InputStream input = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
                    if (input != null) {
                        props.load(input);
                    }
                }

                // Pentru SQLite ne intereseaza doar URL-ul (fara user/parola)
                String url = props.getProperty("db.url", "jdbc:sqlite:output/lab14_ex2.db");
                connection = DriverManager.getConnection(url);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
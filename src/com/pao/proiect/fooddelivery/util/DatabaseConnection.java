package com.pao.proiect.fooddelivery.util;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    private DatabaseConnection() {
        Properties properties = new Properties();
        try (InputStream inputStream = DatabaseConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("Fisierul db.properti    es nu a fost gasit in classpath!");
            }
            properties.load(inputStream);
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            this.connection =  DriverManager.getConnection(url, user, password);
        }
        catch (IOException | SQLException e) {
            // Folosim | (bara verticala) ca sa prindem doua erori diferite in acelasi bloc
            // E echivalentul lui "except (IOError, sqlite3.Error) as e:" din Python
            throw new RuntimeException("Eroare la initializarea conexiunii cu baza de date", e);
        }
    }
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

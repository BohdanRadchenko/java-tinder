package org.tinder.utils;

import org.tinder.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public static void connect() throws DatabaseException {
        try {
            connection = DriverManager.getConnection(
                    Config.getDbConnectionUri(),
                    Config.getDbUser(),
                    Config.getDbPassword()
            );
            if (connection.isClosed()) {
                throw new DatabaseException("Connection is closed!");
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public static Connection getConnection() throws DatabaseException {
        if (connection == null) {
            connect();
            return getConnection();
        }
        return connection;
    }
}




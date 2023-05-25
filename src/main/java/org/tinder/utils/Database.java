package org.tinder.utils;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.tinder.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public static void migrate(String uri) {
        String url = Config.getUrl(uri);
        String user = Config.getUser(uri);
        String password = Config.getPassword(uri);

        FluentConfiguration conf = new FluentConfiguration()
                .cleanDisabled(false)
                .dataSource(url, user, password);
        Flyway flyway = new Flyway(conf);
        flyway.migrate();
    }

    public static void migrate() {
        migrate(Config.getDatabaseUrl());
    }

    public static void connect(String uri) throws DatabaseException {
        try {
            String url = Config.getUrl(uri);
            String user = Config.getUser(uri);
            String password = Config.getPassword(uri);

            connection = DriverManager.getConnection(url, user, password);

            if (connection.isClosed()) {
                throw new DatabaseException("Connection is closed!");
            }
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public static void connect() throws DatabaseException {
        connect(Config.getDatabaseUrl());
    }

    public static Connection getConnection() throws DatabaseException {
        if (connection == null) {
            connect();
            return getConnection();
        }
        return connection;
    }

    public static void close() {
        try {
            if (connection == null) return;
            connection.close();
        } catch (SQLException ex) {
            throw new DatabaseException("Close database exception", ex);
        }
    }
}




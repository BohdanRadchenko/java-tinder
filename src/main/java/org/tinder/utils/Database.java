package org.tinder.utils;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.FluentConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    public static void migrate() {
        FluentConfiguration conf = new FluentConfiguration().dataSource(Config.getDbConnectionUri(), Config.getDbUser(), Config.getDbPassword());
        Flyway flyway = new Flyway(conf);
        flyway.migrate();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(Config.getDbConnectionUri(), Config.getDbUser(), Config.getDbPassword());
    }
}




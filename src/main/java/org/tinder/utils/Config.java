package org.tinder.utils;

import org.tinder.exceptions.DatabaseException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

public class Config {
    enum ConfigKeys {
        PORT("PORT", "8080"),
        JDBC_DB_URL("JDBC_DATABASE_URL", "jdbc:mysql://tinderdb.cs3evdommxcg.eu-north-1.rds.amazonaws.com:3306/dev?user=admin&password=adminadmin"),
        DATABASE_URL("DATABASE_URL", "mysql://admin:adminadmin@tinderdb.cs3evdommxcg.eu-north-1.rds.amazonaws.com:3306/dev");

        private final String key;
        private final String defaultValue;

        ConfigKeys(String key, String defaultValue) {
            this.key = key;
            this.defaultValue = defaultValue;
        }

        public String getKey() {
            return key;
        }

        public String getDefaultValue() {
            return defaultValue;
        }
    }

    private static String getEnvByKey(ConfigKeys keys) {
        return Optional.ofNullable(System.getenv(keys.getKey())).orElse(keys.getDefaultValue());
    }

    public static String getUrl(String uri) throws DatabaseException {
        try {
            URI dbUri = new URI(uri);
            return "jdbc:mysql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();
        } catch (URISyntaxException e) {
            throw new DatabaseException(e);
        }
    }

    public static String getUser(String uri) throws DatabaseException {
        try {
            URI dbUri = new URI(uri);
            return dbUri.getUserInfo().split(":")[0];
        } catch (URISyntaxException e) {
            throw new DatabaseException(e);
        }
    }

    public static String getPassword(String uri) throws DatabaseException {
        try {
            URI dbUri = new URI(uri);
            return dbUri.getUserInfo().split(":")[1];
        } catch (URISyntaxException e) {
            throw new DatabaseException(e);
        }
    }

    public static Integer getPort() {
        try {
            return Integer.parseInt(getEnvByKey(ConfigKeys.PORT));
        } catch (NumberFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getJdbcDatabaseUrl() {
        return getEnvByKey(ConfigKeys.JDBC_DB_URL);
    }

    public static String getDatabaseUrl() {
        return getEnvByKey(ConfigKeys.DATABASE_URL);
    }

}

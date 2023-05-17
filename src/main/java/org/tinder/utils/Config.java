package org.tinder.utils;

import java.util.Optional;

public class Config {
    enum ConfigKeys {
        PORT("PORT", "8080"),
        DB_URI("USE_HOST_ENV_KEY", "jdbc:mysql://tinderdb.cs3evdommxcg.eu-north-1.rds.amazonaws.com:3306"),
        DB_NAME("USE_HOST_ENV_KEY", "dev"),
        DB_USER("USE_HOST_ENV_KEY", "admin"),
        DB_PASSWORD("USE_HOST_ENV_KEY", "adminadmin");

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

    public static Integer getPort() {
        try {
            return Integer.parseInt(getEnvByKey(ConfigKeys.PORT));
        } catch (NumberFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String getDbConnectionUri() {
        String uri = getEnvByKey(ConfigKeys.DB_URI);
        String name = getEnvByKey(ConfigKeys.DB_NAME);
        return String.format("%s/%s", uri, name);
    }

    public static String getDbUser() {
        return getEnvByKey(ConfigKeys.DB_USER);
    }

    public static String getDbPassword() {
        return getEnvByKey(ConfigKeys.DB_PASSWORD);
    }
}

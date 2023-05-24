package org.tinder.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ResulterSet {

    public static String getString(ResultSet rs, String columnName) {
        try {
            return rs.getString(columnName);
        } catch (SQLException ignored) {
            return null;
        }
    }

    public static Integer getInteger(ResultSet rs, String columnName) {
        try {
            return rs.getInt(columnName);
        } catch (SQLException ignored) {
            return null;
        }
    }

    public static Boolean getBoolean(ResultSet rs, String columnName) {
        try {
            return rs.getBoolean(columnName);
        } catch (SQLException ignored) {
            return null;
        }
    }

    public static Timestamp getTimestamp(ResultSet rs, String columnName) {
        try {
            return rs.getTimestamp(columnName);
        } catch (SQLException ignored) {
            return null;
        }
    }
}

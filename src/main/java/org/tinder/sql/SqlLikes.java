package org.tinder.sql;

public class SqlLikes {
    public static String updateLike() {
        return """
                UPDATE user_likes
                SET value = ?
                WHERE user_from = ? AND user_to = ?;
                """;
    }

    public static String insertLike() {
        return """
                INSERT INTO user_likes
                (user_from, user_to, value)
                VALUES (?, ?, ?)
                """;
    }
}

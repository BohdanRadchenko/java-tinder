package org.tinder.sql;

public class SqlUsers {
    public static String selectUserWithLikeWithoutCurrentLimit() {
        return """
                SELECT u.id as user_id,
                       u.email,
                       u.first_name as firstName,
                       u.last_name as lastName,
                       u.avatar
                FROM users u
                    LEFT JOIN users cu ON cu.id = ?
                    LEFT JOIN user_likes ul ON ul.user_from = cu.id AND ul.user_to = u.id
                WHERE u.id <> cu.id
                ORDER BY u.id
                LIMIT ?
                OFFSET ?;
                """;
    }
}

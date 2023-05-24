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

    public static String selectLikedUsersForCurrentUser() {
        return """
                SELECT u.id as user_id,
                       u.email,
                       u.first_name as firstName,
                       u.last_name as lastName,
                       u.avatar,
                       l.time as lastLogin
                FROM user_likes ul
                    LEFT JOIN users u on u.id = ul.user_to
                    LEFT JOIN user_login l on u.id = l.user_id
                WHERE ul.user_from = ? and ul.value = 1
                ORDER BY u.id;
                """;
    }

    public static String selectUserByEmail() {
        return """
                SELECT id as user_id,
                       email,
                       password        
                FROM users                              
                WHERE email = ?
                """;
    }

    public static String updateLastLogin() {
        return "UPDATE user_login SET ip = ? WHERE user_id = ?;";
    }
}

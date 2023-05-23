package org.tinder.sql;

public class SqlChat {

    public static String getChatsForUser() {
        return """
                SELECT 
                    c.id as chat_id,
                    c.uuid as chat,
                    u.avatar as avatar,
                    u.email as email,
                    u.first_name as firstName,
                    u.last_name as lastName
                FROM chat_user cs
                    LEFT JOIN chat_user us on us.chat_id = cs.chat_id and us.user_id <> cs.user_id
                    LEFT JOIN users u on u.id = us.user_id
                    LEFT JOIN chats c on cs.chat_id = c.id
                WHERE cs.user_id = ?;
                """;
    }
}

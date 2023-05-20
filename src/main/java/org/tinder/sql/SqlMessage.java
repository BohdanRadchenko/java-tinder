package org.tinder.sql;

public class SqlMessage {

    public static String getMessageByChatId() {
        return """
                SELECT u.id as user_id,
                    u.email as email,
                    u.first_name as first_name,
                    u.last_name as last_name,
                    u.avatar as avatar,
                    c.uuid as chat,
                    m.id as message_id,
                    m.content as content,
                    mt.type as type,
                    m.created_at as created_at
                FROM messages m
                    left join chats c on c.uuid = ?
                    left join users u on u.id = m.user_from
                    left join messages_types mt on mt.id = m.type
                WHERE chat = c.id
                ORDER BY created_at;
                """;
    }
}

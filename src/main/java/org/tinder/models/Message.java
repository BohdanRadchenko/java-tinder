package org.tinder.models;

import org.tinder.exceptions.DatabaseException;
import org.tinder.interfaces.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public record Message(Integer id, String chat, User from, String content, String type, Long time) implements Model {
    public static Message of(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            Integer messageId = resultSet.getInt("message_id");
            String chat;
            try {
                chat = resultSet.getString("chat");
            } catch (SQLException ignored) {
                chat = null;
            }
            String content = resultSet.getString("content");
            String type = resultSet.getString("type");
            Timestamp createdAt = resultSet.getTimestamp("created_at");

            Integer userId = resultSet.getInt("user_id");
            String email = resultSet.getString("email");
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String avatar = resultSet.getString("avatar");

            User from = User.of(userId, email, firstName, lastName, avatar);
            return new Message(messageId, chat, from, content, type, createdAt.getTime());
        } catch (SQLException ex) {
            throw new DatabaseException("Message load exception", ex);
        }
    }
}
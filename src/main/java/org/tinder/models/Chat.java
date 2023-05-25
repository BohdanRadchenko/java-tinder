package org.tinder.models;


import org.tinder.interfaces.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record Chat(Integer id, String chat, String avatar, String title, String subtitle) implements Model {
    private static final String DEFAULT_AVATAR = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1065&q=80";

    @Override
    public String avatar() {
        return this.avatar == null ? DEFAULT_AVATAR : this.avatar;
    }

    public static Chat of(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            Integer id = resultSet.getInt("chat_id");
            String chat = resultSet.getString("chat");
            String userAvatar = resultSet.getString("avatar");
            String userEmail = resultSet.getString("email");
            String userFirstName = resultSet.getString("firstName");
            String userLastName = resultSet.getString("lastName");
            String subtitle = String.format("%s %s", userFirstName, userLastName);
            return new Chat(id, chat, userAvatar, userEmail, subtitle);
        } catch (SQLException ex) {
            throw new RuntimeException("Chat of result set exception", ex);
        }
    }
}
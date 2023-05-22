package org.tinder.services;

import org.tinder.dao.ChatDao;
import org.tinder.models.Chat;
import org.tinder.utils.Database;

import java.sql.SQLException;
import java.util.List;

public class ChatServices {
    private final ChatDao db = new ChatDao(Database.getConnection());


    public List<Chat> messagesByChatId(Integer userId) {
        try {
            return db.getChatsForUser(userId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

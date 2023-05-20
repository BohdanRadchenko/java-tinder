package org.tinder.services;

import org.tinder.dao.MessageDao;
import org.tinder.models.Message;
import org.tinder.utils.Database;

import java.sql.SQLException;
import java.util.List;

public class MessageServices {
    private final MessageDao db = new MessageDao(Database.getConnection());


    public List<Message> messagesByChatId(String chatId) {
        try {
            return db.getMessagesByChatId(chatId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}

package org.tinder.services;

import org.tinder.dao.MessageDao;
import org.tinder.entities.SocketReqMessage;
import org.tinder.exceptions.DatabaseException;
import org.tinder.models.Message;
import org.tinder.utils.Database;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MessageServices {
    private final MessageDao db = new MessageDao(Database.getConnection());

    public Message getById(int id) {
        try {
            Optional<Message> message = db.getById(id);
            if (message.isEmpty()) {
                throw new DatabaseException(String.format("Message by ID: %d not found!", id));
            }
            return message.get();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public List<Message> getByChatId(String chatId) {
        try {
            return db.getByChatId(chatId);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public int insert(SocketReqMessage message) {
        try {
            return db.create(message);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

}

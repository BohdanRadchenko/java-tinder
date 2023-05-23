package org.tinder.services;

import org.tinder.dao.ChatDao;
import org.tinder.exceptions.DatabaseException;
import org.tinder.exceptions.NotFoundException;
import org.tinder.models.Chat;
import org.tinder.utils.Database;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatServices {
    private final ChatDao db = new ChatDao(Database.getConnection());

    public List<Chat> messagesByChatId(Integer userId) throws DatabaseException {
        try {
            return db.getChatsForUser(userId);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public String getChatUuIdByUserId(Integer currentId, Integer toId) throws DatabaseException, NotFoundException {
        try {
            Optional<String> chatUuid = db.getChatIdByUserFromToId(currentId, toId);
            if (chatUuid.isEmpty()) {
                throw new NotFoundException(String.format(
                        "Chat with user ID: %d and user ID: %d not found",
                        currentId,
                        toId
                ));
            }
            return chatUuid.get();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public String createChatForUsers(Integer currentId, Integer toId) throws DatabaseException {
        try {
            String chatUuId = UUID.randomUUID().toString();
            db.createAndAddUsers(currentId, toId, chatUuId);
            return chatUuId;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public String getOrCreateChatUuidByUserId(Integer currentId, Integer toId) throws DatabaseException {
        try {
            try {
                return getChatUuIdByUserId(currentId, toId);
            } catch (NotFoundException ex) {
                return createChatForUsers(currentId, toId);
            }
        } catch (DatabaseException e) {
            throw new DatabaseException(e);
        }
    }
}

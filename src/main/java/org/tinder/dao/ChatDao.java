package org.tinder.dao;

import org.tinder.interfaces.DAO;
import org.tinder.models.Chat;
import org.tinder.sql.SqlChat;
import org.tinder.utils.SqlRequester;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChatDao implements DAO<Chat> {
    private final Connection connection;

    public ChatDao(Connection connection) {
        this.connection = connection;
    }

    public List<Chat> getChatsForUser(Integer userId) throws SQLException {
        List<Chat> chats = new ArrayList<>();
        ResultSet resultSet = SqlRequester
                .of(connection, SqlChat.getChatsForUser())
                .setInt(userId)
                .query();

        while (resultSet.next()) {
            chats.add(Chat.of(resultSet));
        }
        return chats;
    }

    public Optional<String> getChatIdByUserFromToId(Integer currentId, Integer toId
    ) throws SQLException {
        ResultSet resultSet = SqlRequester
                .of(connection, SqlChat.getChatIdFromTo())
                .setInt(currentId)
                .setInt(toId)
                .query();
        if (!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.of(resultSet.getString(1));
    }


    public int createAndAddUsers(Integer currentId, Integer toId, String uuid) throws SQLException {
        try (
                PreparedStatement stChat = SqlRequester
                        .of(connection, SqlChat.insertChat(), Statement.RETURN_GENERATED_KEYS)
                        .setString(uuid)
                        .statement();
        ) {
            try {
                connection.setAutoCommit(false);
                int rows = stChat.executeUpdate();
                int chatId;

                if (rows == 0) {
                    throw new SQLException("Creating chat failed, no rows affected.");
                }

                try (ResultSet rs = stChat.getGeneratedKeys()) {
                    if (rs.next()) {
                        chatId = rs.getInt(1);
                    } else {
                        throw new SQLException("Creating chat failed, no generated rs.");
                    }
                }
                
                SqlRequester
                        .of(connection, SqlChat.insertUsersChat())
                        .setInt(chatId)
                        .setInt(currentId)
                        .execute();
                SqlRequester
                        .of(connection, SqlChat.insertUsersChat())
                        .setInt(chatId)
                        .setInt(toId)
                        .execute();

                connection.commit();
                connection.setAutoCommit(true);
                return chatId;
            } catch (Exception ex) {
                connection.rollback();
                connection.setAutoCommit(true);
                throw new SQLException(ex);
            }
        }
    }

    @Override
    public int create(Chat chat) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public int update(Chat chat) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public Optional<Chat> getById(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

}

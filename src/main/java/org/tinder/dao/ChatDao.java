package org.tinder.dao;

import org.tinder.interfaces.DAO;
import org.tinder.models.Chat;
import org.tinder.models.Message;
import org.tinder.servlets.MessagesServlet;
import org.tinder.sql.SqlChat;
import org.tinder.sql.SqlMessage;
import org.tinder.utils.SqlRequester;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    @Override
    public boolean delete(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public Optional<Chat> getById(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public int create(Chat chat) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public int update(Chat chat) throws SQLException {
        throw new RuntimeException("Not implement");
    }
}

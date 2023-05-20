package org.tinder.dao;

import org.tinder.interfaces.DAO;
import org.tinder.models.Message;
import org.tinder.servlets.MessagesServlet;
import org.tinder.sql.SqlMessage;
import org.tinder.utils.SqlRequester;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageDao implements DAO<Message> {
    private final Connection connection;

    public MessageDao(Connection connection) {
        this.connection = connection;
    }

    public List<Message> getMessagesByChatId(String chatId) throws SQLException {
        List<Message> messages = new ArrayList<>();
        ResultSet resultSet = SqlRequester
                .of(connection, SqlMessage.getMessageByChatId())
                .setString(chatId)
                .query();
        while (resultSet.next()) {
            messages.add(Message.load(resultSet));
        }
        return messages;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        throw new RuntimeException("Not implement");
    }

    @Override
    public Optional<Message> getById(Integer id) throws Exception {
        throw new RuntimeException("Not implement");
    }

    @Override
    public int create(Message message) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public int update(Message message) throws SQLException {
        throw new RuntimeException("Not implement");
    }
}

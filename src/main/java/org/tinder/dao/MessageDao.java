package org.tinder.dao;

import org.tinder.entities.SocketReqMessage;
import org.tinder.interfaces.DAO;
import org.tinder.models.Message;
import org.tinder.models.User;
import org.tinder.sql.SqlMessage;
import org.tinder.utils.SqlRequester;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageDao implements DAO<Message> {
    private final Connection connection;

    public MessageDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public Optional<Message> getById(Integer id) throws SQLException {
        ResultSet rs = SqlRequester
                .of(connection, SqlMessage.selectMessageById())
                .setInt(id)
                .query();
        if (!rs.next()) {
            return Optional.empty();
        } else {
            return Optional.of(Message.of(rs));
        }
    }

    public List<Message> getByChatId(String chatId) throws SQLException {
        List<Message> messages = new ArrayList<>();
        ResultSet resultSet = SqlRequester
                .of(connection, SqlMessage.selectMessageByChatId())
                .setString(chatId)
                .query();
        while (resultSet.next()) {
            messages.add(Message.of(resultSet));
        }
        return messages;
    }

    @Override
    public int create(Message message) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    public int create(SocketReqMessage message) throws SQLException {
        try (
                PreparedStatement statement = SqlRequester
                        .of(connection, SqlMessage.insertMessageFromSocket(), Statement.RETURN_GENERATED_KEYS)
                        .setString(message.chatId())
                        .setInt(message.userId())
                        .setString(message.message())
                        .setInt(message.type())
                        .statement()
        ) {
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating messaged failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating messaged failed, no generated keys.");
                }
            }
        }
    }

    @Override
    public int update(Message message) throws SQLException {
        throw new RuntimeException("Not implement");
    }
}

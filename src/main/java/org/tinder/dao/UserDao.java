package org.tinder.dao;

import org.tinder.models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao extends DAO<User> {
    public UserDao(Connection connection) {
        super(connection);
    }

    @Override
    public int create(User user) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public int update(User user) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public boolean delete(String id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public Optional<User> get(String id) throws SQLException {
        throw new RuntimeException("Not implement");
    }
}

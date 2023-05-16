package org.tinder.dao;

import org.tinder.interfaces.DAO;
import org.tinder.models.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserDao implements DAO<User> {
    private final Connection connection;
    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        throw new RuntimeException("Not implement");
    }

    @Override
    public Optional<User> getById(Integer id) throws Exception {
        throw new RuntimeException("Not implement");
    }

    @Override
    public int create(User user) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public int update(User user) throws SQLException {
        throw new RuntimeException("Not implement");
    }
}

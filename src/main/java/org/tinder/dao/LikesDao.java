package org.tinder.dao;

import org.tinder.interfaces.DAO;
import org.tinder.models.Like;
import org.tinder.sql.SqlLikes;
import org.tinder.utils.SqlRequester;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class LikesDao implements DAO<Like> {
    private final Connection connection;

    public LikesDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Like> getById(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public int create(Like like) throws SQLException {
        return SqlRequester.of(connection, SqlLikes.insertLike())
                .setInt(like.from())
                .setInt(like.to())
                .setInt(like.value())
                .update();
    }

    @Override
    public int update(Like like) throws SQLException {
        return SqlRequester.of(connection, SqlLikes.updateLike())
                .setInt(like.value())
                .setInt(like.from())
                .setInt(like.to())
                .update();
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }
}

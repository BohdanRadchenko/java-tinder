package org.tinder.dao;

import org.tinder.interfaces.DAO;
import org.tinder.models.User;
import org.tinder.sql.SqlUsers;
import org.tinder.utils.SqlRequester;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements DAO<User> {
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    public Optional<User> getUserLikeWithoutCurrentLimit(Integer withoutId, Integer limit, Integer offset) throws SQLException {
        ResultSet rs = SqlRequester
                .of(connection, SqlUsers.selectUserWithLikeWithoutCurrentLimit())
                .setInt(withoutId)
                .setInt(limit)
                .setInt(offset)
                .query();
        if (!rs.next()) {
            return Optional.empty();
        } else {
            return Optional.of(User.of(rs));
        }
    }

    public List<User> getLikedUsers(Integer currentUserId) throws SQLException {
        ArrayList<User> users = new ArrayList<>();
        ResultSet rs = SqlRequester
                .of(connection, SqlUsers.selectLikedUsersForCurrentUser())
                .setInt(currentUserId)
                .query();
        while (rs.next()) {
            users.add(User.of(rs));
        }
        return users;
    }

    @Override
    public Optional<User> getById(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    public Optional<User> getByEmail(String email) throws SQLException {
        ResultSet rs = SqlRequester
                .of(connection, SqlUsers.selectUserByEmail())
                .setString(email)
                .query();
        if (!rs.next()) {
            System.out.println("Empty");
            return Optional.empty();
        } else {
            System.out.println("Else");
            return Optional.of(User.of(rs));
        }
    }

    @Override
    public int create(User user) throws SQLException {

        //create сдесь создаём
        throw new RuntimeException("Not implement");
    }

    @Override
    public int update(User user) throws SQLException {
        throw new RuntimeException("Not implement");
    }

    @Override
    public boolean delete(Integer id) throws SQLException {
        throw new RuntimeException("Not implement");
    }
}

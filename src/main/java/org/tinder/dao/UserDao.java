package org.tinder.dao;

import org.tinder.interfaces.DAO;
import org.tinder.models.User;
import org.tinder.sql.SqlMessage;
import org.tinder.sql.SqlUsers;
import org.tinder.utils.SqlRequester;

import java.sql.*;
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
        ResultSet rs = SqlRequester
                .of(connection, SqlUsers.selectUserById())
                .setInt(id)
                .query();
        if (!rs.next()) {
            return Optional.empty();
        } else {
            return Optional.of(User.of(rs));
        }
    }

    public Optional<User> getByEmail(String email) throws SQLException {
        ResultSet rs = SqlRequester
                .of(connection, SqlUsers.selectUserByEmail())
                .setString(email)
                .query();
        if (!rs.next()) {
            return Optional.empty();
        } else {
            return Optional.of(User.of(rs));
        }
    }

    public boolean insertLastLogin(Integer id, String ip) throws SQLException {
        int update = SqlRequester
                .of(connection, SqlUsers.insertLastLogin())
                .setInt(id)
                .setString(ip)
                .update();
        return update >= 1;
    }

    public boolean updateLastLogin(Integer id, String ip) throws SQLException {
        int update = SqlRequester
                .of(connection, SqlUsers.updateLastLogin())
                .setString(ip)
                .setInt(id)
                .update();
        return update >= 1;
    }

    @Override
    public int create(User user) throws SQLException {
        try (
                PreparedStatement statement = SqlRequester
                        .of(connection, SqlUsers.insertUserRegister(), Statement.RETURN_GENERATED_KEYS)
                        .setString(user.email(), user.password(), user.firstName(), user.lastName())
                        .statement()
        ) {
            int affectedRows = statement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no generated keys.");
                }
            }
        }
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

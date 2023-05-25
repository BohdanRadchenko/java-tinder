package org.tinder.services;

import org.tinder.dao.UserDao;
import org.tinder.exceptions.DatabaseException;
import org.tinder.exceptions.NotFoundException;
import org.tinder.models.User;
import org.tinder.utils.Database;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserServices {
    private final UserDao db = new UserDao(Database.getConnection());

    public User getUserForShow(Integer currentId, Integer offset) throws DatabaseException, NotFoundException {
        try {
            Optional<User> user = db.getUserLikeWithoutCurrentLimit(currentId, 1, offset);
            if (user.isEmpty()) {
                throw new NotFoundException(String.format(
                        "User to show in users page not found! Current user ID: %d, offset: %d",
                        currentId,
                        offset
                ));
            }
            return user.get();
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public List<User> getLikedUsers(Integer currentId) throws DatabaseException, NotFoundException {
        try {
            List<User> likedUsers = db.getLikedUsers(currentId);
            if (likedUsers.isEmpty()) {
                throw new NotFoundException(String.format("Users with like for user ID: %d not found", currentId));
            }
            return likedUsers;
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public User getByEmail(String email) throws NotFoundException, DatabaseException {
        try {
            Optional<User> byEmail = db.getByEmail(email);
            if (byEmail.isEmpty()) {
                throw new NotFoundException("User not found");
            }
            return byEmail.get();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public User getById(Integer id) throws NotFoundException, DatabaseException {
        try {
            Optional<User> user = db.getById(id);
            if (user.isEmpty()) {
                throw new NotFoundException("User not found");
            }
            return user.get();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    public boolean insertLastLogin(Integer id, String ip) throws DatabaseException {
        try {
            return db.insertLastLogin(id, ip);
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public boolean updateLastLogin(Integer id, String ip) throws DatabaseException {
        try {
            return db.updateLastLogin(id, ip);
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public User login(String email, String password) throws NotFoundException, DatabaseException, IllegalArgumentException {
        User user = getByEmail(email);
        boolean equalsPassword = user.password().equals(password);
        if (!equalsPassword) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }

    public int create(User user) throws DatabaseException {
        try {
            return db.create(user);
        } catch (SQLException ex) {
            throw new DatabaseException(ex);
        }
    }

    public int register(User user, String ip) throws DatabaseException {
        int id = create(user);
        insertLastLogin(id, ip);
        return id;
    }
}

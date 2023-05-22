package org.tinder.services;

import org.tinder.dao.UserDao;
import org.tinder.exceptions.DatabaseException;
import org.tinder.exceptions.NotFoundException;
import org.tinder.models.User;
import org.tinder.utils.Database;

import java.sql.SQLException;
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
}

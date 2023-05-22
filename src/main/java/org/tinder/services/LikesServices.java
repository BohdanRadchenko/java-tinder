package org.tinder.services;

import org.tinder.dao.LikesDao;
import org.tinder.exceptions.DatabaseException;
import org.tinder.exceptions.NotFoundException;
import org.tinder.models.Like;
import org.tinder.utils.Database;

import java.sql.SQLException;

public class LikesServices {
    private final LikesDao db = new LikesDao(Database.getConnection());

    public boolean createOrUpdate(Like like) throws DatabaseException, NotFoundException {
        try {
            int updateLines = db.update(like);
            if (updateLines >= 1) return true;
            if (db.create(like) < 1) {
                throw new DatabaseException("Like was not created!");
            }
            return true;
        } catch (SQLException | DatabaseException ex) {
            throw new DatabaseException(ex);
        }
    }
}

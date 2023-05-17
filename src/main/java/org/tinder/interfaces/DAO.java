package org.tinder.interfaces;

import java.sql.SQLException;
import java.util.Optional;

public interface DAO<T extends Model> {
    boolean delete(Integer id) throws Exception;
    Optional<T> getById(Integer id) throws Exception;
    int create(T model) throws SQLException;

    int update(T model) throws SQLException;

    default boolean save(T model) throws SQLException {
        if (model.id() == null) {
            return create(model) >= 1;
        }
        return update(model) >= 1;
    }
}

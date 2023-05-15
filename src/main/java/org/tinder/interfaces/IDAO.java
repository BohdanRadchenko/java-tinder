package org.tinder.interfaces;

import java.util.Optional;

public interface IDAO<T extends Model> {

    boolean save(T model) throws Exception;

    boolean delete(String id) throws Exception;

    Optional<T> get(String id) throws Exception;
}

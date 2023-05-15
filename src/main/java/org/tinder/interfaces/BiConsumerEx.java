package org.tinder.interfaces;

import java.util.function.BiConsumer;

public interface BiConsumerEx<T, U> extends BiConsumer<T, U> {
    void acceptEx(T t, U u) throws Exception;

    @Override
    default void accept(T t, U u) {
        try {
            acceptEx(t, u);
        } catch (Exception x) {
            throw new RuntimeException(x);
        }
    }
}

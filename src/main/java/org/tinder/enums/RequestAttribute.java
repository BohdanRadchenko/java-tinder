package org.tinder.enums;

public enum RequestAttribute {
    USER_ID("user_id"),
    CHAT_ID("chat_id"),
    USERS_OFFSET("users_offset");

    private final String value;

    RequestAttribute(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

    @Override
    public String toString() {
        return value();
    }
}

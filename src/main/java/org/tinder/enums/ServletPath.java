package org.tinder.enums;

public enum ServletPath {
    WILDCARD("/*"),
    STATIC("/static"),
    STATIC_WILDCARD("/static/*"),
    HOME("/"),
    LOGIN("/login"),
    REGISTER("/register"),
    LOGOUT("/logout"),
    USERS("/users"),
    MESSAGES("/messages"),
    MESSAGES_WILDCARD("/messages/*");

    private final String path;

    ServletPath(String path) {
        this.path = path;
    }

    public String path() {
        return this.path;
    }
}

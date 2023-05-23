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
    LIKES("/liked"),
    MESSAGES("/messages"),
    MESSAGES_WILDCARD("/messages/*");

    private final String path;

    ServletPath(String path) {
        this.path = path;
    }

    public String path() {
        return this.path;
    }

    public String of(String path) {
        return String.format("%s/%s", this.path, path);
    }
}

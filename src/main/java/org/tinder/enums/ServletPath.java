package org.tinder.enums;

public enum ServletPath {
    STATIC_WILDCARD("/static/*"),
    HOME("/"),
    LOGIN("/login"),
    REGISTER("/register"),
    LOGOUT("/logout");

    private final String path;

    ServletPath(String path) {
        this.path = path;
    }

    public String path() {
        return this.path;
    }
}

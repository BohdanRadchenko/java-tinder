package org.tinder.models;


import org.tinder.interfaces.Model;

public record User(Integer id, String login, String email, String firstName, String lastName,
                   String avatar) implements Model {

    public String fullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public static User of(Integer id, String email, String firstName, String lastName, String avatar) {
        return new User(id, null, email, firstName, lastName, avatar);
    }
}
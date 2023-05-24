package org.tinder.models;


import org.tinder.exceptions.DatabaseException;
import org.tinder.interfaces.Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public record User(Integer id, String email, String password, String profession, String firstName, String lastName,
                   String avatar, Boolean like) implements Model {

    private static final String DEFAULT_AVATAR = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1065&q=80";
    
    public String fullName() {
        return String.format("%s %s", firstName, lastName);
    }

    @Override
    public String avatar() {
        return this.avatar != null ? this.avatar : DEFAULT_AVATAR;
    }

    public static User of(Integer id, String email, String firstName, String lastName, String avatar) {
        return new User(id, email, null, null, firstName, lastName, avatar, null);
    }


    public static User of(Integer id, String email, String firstName, String lastName, String avatar, Boolean like) {
        return new User(id, email, null, null, firstName, lastName, avatar, like);
    }

    public static User of(String email, String password, String profession, String firstName, String lastName) {
        return new User(null, email, password, profession, firstName, lastName, null, null);
    }

    public static User of(Integer id, String email, String password, String profession, String firstName, String lastName, String avatar, Boolean like) {
        return new User(id, email, password, profession, firstName, lastName, avatar, like);
    }


    public static User of(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            Integer id = resultSet.getInt("user_id");
            String email = resultSet.getString("email");
            String password;
            try {
                password = resultSet.getString("password");
            } catch (SQLException ignored) {
                password = null;
            }
            String profession;
            try {
                profession = resultSet.getString("profession");
            } catch (SQLException ignored) {
                profession = null;
            }

            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String avatar = resultSet.getString("avatar");
            Boolean like;
            try {
                like = resultSet.getBoolean("like");
            } catch (SQLException ignored) {
                like = null;
            }
            return User.of(id, email, password, profession, firstName, lastName, avatar, like);
        } catch (SQLException ex) {
            throw new DatabaseException("User load exception", ex);
        }
    }
}
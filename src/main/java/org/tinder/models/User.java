package org.tinder.models;


import org.ocpsoft.prettytime.PrettyTime;
import org.tinder.exceptions.DatabaseException;
import org.tinder.interfaces.Model;
import org.tinder.utils.DateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public record User(Integer id, String email, String firstName, String lastName,
                   String avatar, Boolean like, Long lastLogin) implements Model {

    private static final String DEFAULT_AVATAR = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1065&q=80";

    public String profession() {
        return "Builder Sales Agent";
    }

    public String fullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public String lastLoginDate() {
        if (this.lastLogin == null) return "Null";
        return DateUtil.of(this.lastLogin).formatter("d/MM/yyyy");
    }

    public String lastLoginAgo() {
        if (this.lastLogin == null) return "Null";
        PrettyTime p = new PrettyTime();
        return p.format(new Date(this.lastLogin));
    }

    @Override
    public String avatar() {
        return this.avatar != null ? this.avatar : DEFAULT_AVATAR;
    }

    public static User of(Integer id, String email, String firstName, String lastName, String avatar) {
        return new User(id, email, firstName, lastName, avatar, null, null);
    }

    public static User of(Integer id, String email, String firstName, String lastName, String avatar, Boolean like) {
        return new User(id, email, firstName, lastName, avatar, like, null);
    }

    public static User of(Integer id, String email, String firstName, String lastName, String avatar, Boolean like, Long lastLogin) {
        return new User(id, email, firstName, lastName, avatar, like, lastLogin);
    }

    public static User of(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            Integer id = resultSet.getInt("user_id");
            String email = resultSet.getString("email");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            String avatar = resultSet.getString("avatar");

            Boolean like;
            try {
                like = resultSet.getBoolean("like");
            } catch (SQLException ignored) {
                like = null;
            }

            Long lastLogin;
            try {
                Timestamp time = resultSet.getTimestamp("lastLogin");
                if (time == null) {
                    lastLogin = null;
                } else {
                    lastLogin = time.getTime();
                }
            } catch (SQLException ignored) {
                lastLogin = null;
            }

            return User.of(id, email, firstName, lastName, avatar, like, lastLogin);
        } catch (SQLException ex) {
            throw new DatabaseException("User load exception", ex);
        }
    }
}
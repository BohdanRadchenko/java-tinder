package org.tinder.models;

import org.ocpsoft.prettytime.PrettyTime;
import org.tinder.interfaces.Model;
import org.tinder.utils.DateUtil;
import org.tinder.utils.ResulterSet;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

public record User(Integer id, String email, String password, String profession, String firstName, String lastName,
                   String avatar, Boolean like, Long lastLogin) implements Model {

    private static final String DEFAULT_AVATAR = "https://images.unsplash.com/photo-1503023345310-bd7c1de61c7d?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1065&q=80";

    @Override
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
        return new User(id, email, null, null, firstName, lastName, avatar, null, null);
    }

    public static User of(Integer id, String email, String firstName, String lastName, String avatar, Boolean like) {
        return new User(id, email, null, null, firstName, lastName, avatar, like, null);
    }

    public static User of(Integer id, String email, String firstName, String lastName, String avatar, Boolean like, Long lastLogin) {
        return new User(id, email, null, null, firstName, lastName, avatar, like, lastLogin);
    }

    public static User of(Integer id, String email, String password, String profession, String firstName, String lastName, String avatar, Boolean like, Long lastLogin) {
        return new User(id, email, password, profession, firstName, lastName, avatar, like, lastLogin);
    }

    public static User of(ResultSet resultSet) {
        if (resultSet == null) return null;
        Integer id = ResulterSet.getInteger(resultSet, "user_id");
        String email = ResulterSet.getString(resultSet, "email");
        String password = ResulterSet.getString(resultSet, "password");
        String profession = ResulterSet.getString(resultSet, "profession");
        String firstName = ResulterSet.getString(resultSet, "firstName");
        String lastName = ResulterSet.getString(resultSet, "lastName");
        String avatar = ResulterSet.getString(resultSet, "avatar");
        Boolean like = ResulterSet.getBoolean(resultSet, "like");
        Timestamp time = ResulterSet.getTimestamp(resultSet, "lastLogin");
        Long lastLogin = time == null ? null : time.getTime();

        return User.of(id, email, password, profession, firstName, lastName, avatar, like, lastLogin);
    }
}
package org.tinder.utils;

import java.sql.*;

public class AppConnection {
    static void selectAll(Connection conn) throws SQLException {
        //                  int, string
        String sql =
                """
                        SELECT
                          *
                        FROM
                          users
                        """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("email");
            Timestamp timestamp = rs.getTimestamp("ts");
            Timestamp datetime = rs.getTimestamp("dt");
            System.out.printf("user: id: %d, name: %s, timestamp: %s, datetime: %s\n", id, name, timestamp,datetime);
        }
    }

    static void insertOne(Connection conn, String email, String password) throws SQLException {
        String sql =
                """
                        INSERT INTO users (
                        email,
                        password
                        )
                        VALUES (
                        ?,
                        ?
                        )
                        """;
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, password);
        stmt.execute();
    }


    public static void main(String[] args) throws SQLException {
        Connection conn = Database.connect();
        selectAll(conn);
        insertOne(conn,"Alia@gmail.com", "0000234");
       selectAll(conn);
    }
}

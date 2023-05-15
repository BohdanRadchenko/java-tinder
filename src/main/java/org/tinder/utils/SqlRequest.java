package org.tinder.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
* usage
* sql = "SELECT * ...."
* sr = SqlRequest.of(Database.getConnection(), sql)
* sr.setString(data).execute()
*
* or
*
* ResultSet rs = SqlRequest
                .of(connection, sql)
                .setString(d)
                .setString(d)
                .setString(d)
                .setInt(d)
                .setString(d)
                .setString(d)
                .setString(d)
                .query();
* */

public class SqlRequest {
    private final Connection connection;
    private final String sql;
    private final PreparedStatement statement;
    private int statementCount = 1;

    public SqlRequest(Connection connection, String sql) throws SQLException {
        this.connection = connection;
        this.sql = sql;
        this.statement = connection.prepareStatement(sql);
    }

    public SqlRequest setString(int parameterIndex, String x) throws SQLException {
        statement.setString(parameterIndex, x);
        return this;
    }

    public SqlRequest setString(String x) throws SQLException {
        return setString(statementCount++, x);
    }

    public SqlRequest setString(String... xs) throws SQLException {
        for (String s : xs) {
            setString(s);
        }
        return this;
    }

    public SqlRequest setInt(int parameterIndex, int x) throws SQLException {
        statement.setInt(parameterIndex, x);
        return this;
    }

    public SqlRequest setInt(int x) throws SQLException {
        return setInt(statementCount++, x);
    }

    public ResultSet query() throws SQLException {
        return statement.executeQuery();
    }

    public int update() throws SQLException {
        return statement.executeUpdate();
    }

    public boolean execute() throws SQLException {
        return statement.execute();
    }

    public static SqlRequest of(Connection connection, String sql) throws SQLException {
        return new SqlRequest(connection, sql);
    }
}

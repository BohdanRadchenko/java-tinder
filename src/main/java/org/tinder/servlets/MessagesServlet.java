package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.exceptions.DatabaseException;
import org.tinder.utils.Database;
import org.tinder.utils.FMTemplate;
import org.tinder.utils.SqlRequester;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class MessagesServlet extends HttpServlet {
    public record User(Integer id, String email, String name, String avatar) {
        public static User load(ResultSet resultSet) {
            if (resultSet == null) return null;
            try {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                String avatar = resultSet.getString("avatar");
                return new User(id, email, "Name", avatar);
            } catch (SQLException ignored) {
                return null;
            }
        }
    }

    public record Chat(Integer id, String chatId, String avatar, String title, String subtitle) {
        public static Chat load(ResultSet resultSet) {
            if (resultSet == null) return null;
            try {
                String chatId = resultSet.getString("chatId");
                String avatar = resultSet.getString("avatar");
                String title = resultSet.getString("email");
                return new Chat(null, chatId, avatar, title, "Full name");
            } catch (SQLException ignored) {
                return null;
            }
        }
    }

    public record Message(User from, String msg, String type, String time) {
        public static Message load(ResultSet resultSet) {
            if (resultSet == null) return null;
            try {
                String content = resultSet.getString("content");
                String type = resultSet.getString("type");
                String time = "Jan 25, 6:20 PM";
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                if (createdAt != null) {
                    time = new Date(createdAt.getTime()).toString();
                }
                return new Message(User.load(resultSet), content, type, time);
            } catch (SQLException ex) {
                throw new DatabaseException("Message load exception", ex);
            }
        }
    }

    private List<Chat> getUserChats(int id) {
        String sql = """
                SELECT c.uuid as chatId, u.avatar as avatar, u.email as email
                FROM chat_user cs
                LEFT JOIN chat_user us on us.chat_id = cs.chat_id and us.user_id <> cs.user_id
                LEFT JOIN users u on u.id = us.user_id
                LEFT JOIN chats c on cs.chat_id = c.id
                WHERE cs.user_id = ?;
                """;
        ArrayList<Chat> chats = new ArrayList<>();
        try {
            ResultSet resultSet = SqlRequester
                    .of(Database.getConnection(), sql)
                    .setInt(id)
                    .query();
            while (resultSet.next()) {
                chats.add(Chat.load(resultSet));
            }
            return chats;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private List<Message> getChatMessages(String chatId) {
        List<Message> messages = new ArrayList<>();
        if (chatId == null || chatId.isBlank()) return messages;
        String sql = """
                SELECT u.id as id, u.email as email, u.avatar as avatar, m.content as content, mt.type as type, m.created_at as created_at
                FROM messages m
                    left join chats c on c.uuid = ?
                    left join users u on u.id = m.user_from
                    left join messages_types mt on mt.id = m.type
                WHERE chat = c.id
                ORDER BY created_at;
                """;
        try {
            ResultSet resultSet = SqlRequester
                    .of(Database.getConnection(), sql)
                    .setString(chatId)
                    .query();
            while (resultSet.next()) {
                messages.add(Message.load(resultSet));
            }
            return messages;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String chatId = "57cd7a2a-a142-40f7-935e-7591af9d7bd6";
        int userId = 1;
        String chatId = (String) req.getAttribute("chatId");
//        String userId = (String) req.getAttribute("userId");

        HashMap<String, Object> data = new HashMap<>();
        List<Chat> userChats = getUserChats(userId);
        Chat currentChat = userChats
                .stream()
                .filter(ch -> ch.chatId.equals(chatId))
                .findFirst()
                .orElse(null);
        List<Message> messages = getChatMessages(chatId);

        data.put("chats", userChats);
        data.put("currentChat", currentChat);
        data.put("messages", messages);
        data.put("chatId", chatId);
        data.put("userId", userId);

        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("chat.ftl")
                    .process(data, w);
        } catch (TemplateException x) {
            throw new RuntimeException(x);
        }
    }
}

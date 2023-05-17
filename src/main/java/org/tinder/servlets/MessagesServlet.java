package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.utils.Database;
import org.tinder.utils.FMTemplate;
import org.tinder.utils.SqlRequester;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MessagesServlet extends HttpServlet {
    public record User(Integer id, String email) {
        public static User load(ResultSet resultSet) {
            if (resultSet == null) return null;
            try {
                int id = resultSet.getInt("id");
                String email = resultSet.getString("email");
                return new User(id, email);
            } catch (SQLException ignored) {
                return null;
            }
        }
    }

    public record Chat(Integer id, String chatId, User to, String lastMessage) {
        public static Chat load(ResultSet resultSet) {
            if (resultSet == null) return null;
            try {
                String chatId = resultSet.getString("chatId");
                System.out.println("chatId: " + chatId);
                return new Chat(null, chatId, User.load(resultSet), "not implemented");
            } catch (SQLException ignored) {
                return null;
            }
        }
    }

    public record Message(User from, String msg) {
        public static Message load(ResultSet resultSet) {
            if (resultSet == null) return null;
            try {
                String content = resultSet.getString("content");
                return new Message(User.load(resultSet), content);
            } catch (SQLException ignored) {
                return null;
            }
        }
    }


    private List<Chat> getUserChats(int id) {
        String sql = """
                SELECT c.uuid as chatId, u.id as id, u.email as email
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
        String sql = """
                SELECT u.id as id, u.email as email, m.content as content
                                      FROM messages m
                                      left join chats c on c.uuid = ?
                                      left join users u on u.id = m.user_from
                                      WHERE chat = c.id
                                      order by created_at;
                """;
        List<Message> messages = new ArrayList<>();
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
        String chatId = "57cd7a2a-a142-40f7-935e-7591af9d7bd6";
        int userId = 1;

//        String chatId = (String) req.getAttribute("chatId");
        HashMap<String, Object> data = new HashMap<>();
        List<Chat> userChats = getUserChats(userId);
        List<Message> messages = getChatMessages(chatId);

//        ArrayList<Chat> chats = new ArrayList<>();
//        String[] strings = {"Hii", "hiii\nHow are you ?", "nice\nAre you fine ?", "Yes always", "Byy", "Byy", "Byy", "msg text 1", "msg text 2", "msg text 3"};
//
//        ArrayList<Message> messages = new ArrayList<>();
//        IntStream.range(0, 10).forEach(c -> {
//            String id = UUID.randomUUID().toString();
//            User user = new User(c, "mail@mail.com");
//            chats.add(new Chat(id, user, String.format("last message %d", c)));
//            messages.add(Message.create(strings[c]));
//        });

//        data.put("chats", chats);
//        data.put("messages", messages);
//        data.put("chatId", chatId);

        data.put("chats", userChats);
        data.put("messages", messages);
        data.put("chatId", chatId);

        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("chat.ftl")
                    .process(data, w);
        } catch (TemplateException x) {
            throw new RuntimeException(x);
        }
    }
}

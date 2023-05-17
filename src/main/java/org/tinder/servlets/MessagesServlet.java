package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.models.User;
import org.tinder.utils.FMTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

public class MessagesServlet extends HttpServlet {

    public record Chat(String id, User from, String lastMessage) {}
    public record Message(User from, String msg) {
        static Message create(String msg) {
            int id = new Random().nextInt(2);
            String email = id == 1 ? "first@mail.com" : "second@mail.com";
            return  new Message(new User(id, email), msg);
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String chatId = (String) req.getAttribute("chatId");
        HashMap<String, Object> data = new HashMap<>();

        ArrayList<Chat> chats = new ArrayList<>();
        String[] strings = {"Hii", "hiii\nHow are you ?", "nice\nAre you fine ?", "Yes always", "Byy", "Byy", "Byy", "msg text 1", "msg text 2", "msg text 3"};

        ArrayList<Message> messages = new ArrayList<>();
        IntStream.range(0, 10).forEach(c -> {
            String id = UUID.randomUUID().toString();
            User user = new User(c, "mail@mail.com");
            chats.add(new Chat(id, user, String.format("last message %d", c)));
            messages.add(Message.create(strings[c]));
        });

        data.put("chats", chats);
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

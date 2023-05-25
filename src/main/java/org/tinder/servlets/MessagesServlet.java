package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.entities.SocketResMessage;
import org.tinder.enums.RequestAttribute;
import org.tinder.models.Chat;
import org.tinder.services.ChatServices;
import org.tinder.services.MessageServices;
import org.tinder.utils.FMTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class MessagesServlet extends HttpServlet {
    private final MessageServices messageServices = new MessageServices();
    private final ChatServices chatServices = new ChatServices();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getAttribute(RequestAttribute.USER_ID.value());
        String chatId = (String) req.getAttribute(RequestAttribute.CHAT_ID.value());
        System.out.println(chatId);

        HashMap<String, Object> data = new HashMap<>();

        List<Chat> userChats = chatServices.messagesByChatId(userId);

        List<SocketResMessage> messages = messageServices
                .getByChatId(chatId)
                .stream()
                .map(SocketResMessage::of)
                .toList();

        Chat currentChat = userChats
                .stream()
                .filter(ch -> ch.chat().equals(chatId))
                .findFirst()
                .orElse(null);

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

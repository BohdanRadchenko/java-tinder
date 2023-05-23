package org.tinder.entities;

import com.google.gson.Gson;
import org.tinder.models.Message;
import org.tinder.models.User;

public class SocketResMessage {
    private static final Gson gson = new Gson();
    private final MessageContent content;
    private final User from;

    public SocketResMessage(Message message) {
        this.from = message.from();
        this.content = new MessageContent(message);
    }

    public User from() {
        return this.from;
    }

    public MessageContent content() {
        return this.content;
    }

    public String toJson() {
        return gson.toJson(this);
    }

    public static SocketResMessage of(Message message) {
        return new SocketResMessage(message);
    }
}

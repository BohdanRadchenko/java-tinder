package org.tinder.entities;

import org.tinder.models.Message;
import org.tinder.utils.DateUtil;

public class MessageContent {
    private String message;
    private String type;
    private String time;

    public MessageContent(Message message) {
        this.message = message.content();
        this.type = message.type();
        this.time = DateUtil
                .of(message.time())
                .formatterAgo("MMM dd, HH:mm");
    }

    public String message() {
        return this.message;
    }

    public String type() {
        return this.type;
    }

    public String time() {
        return this.time;
    }

    @Override
    public String toString() {
        return String.format("Content[%s, %s, %s]", message, type, time);
    }
}

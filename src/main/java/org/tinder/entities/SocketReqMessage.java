package org.tinder.entities;

import com.google.gson.Gson;
import org.tinder.enums.MessageType;

public record SocketReqMessage(Integer userId, String chatId, String message) {
    private static final Gson gson = new Gson();

    public Integer type() {
        MessageType messageType = MessageType.checkType(message);
        return messageType.code();
    }

    public static SocketReqMessage of(String json) {
        return gson.fromJson(json, SocketReqMessage.class);
    }
}

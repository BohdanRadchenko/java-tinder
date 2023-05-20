package org.tinder.entities;

import com.google.gson.Gson;

public record SocketOnMessage(Integer id, String message) {
    private final static Gson gson = new Gson();

    public static SocketOnMessage of(String json) {
        return gson.fromJson(json, SocketOnMessage.class);
    }
}

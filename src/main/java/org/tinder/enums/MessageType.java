package org.tinder.enums;

import java.util.regex.Pattern;

public enum MessageType {
    TEXT(1), LINK(2);

    private final int code;

    MessageType(int code) {
        this.code = code;
    }

    public int code() {
        return this.code;
    }

    public static MessageType checkType(String message) {
        String regex = "(https?:\\/\\/[^\\s]+)";
        boolean matches = Pattern.matches(regex, message);
        return matches ? MessageType.LINK : MessageType.TEXT;
    }
}

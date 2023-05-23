package org.tinder.filters;

import org.tinder.enums.RequestAttribute;
import org.tinder.enums.ServletPath;
import org.tinder.utils.QueryString;
import org.tinder.utils.Respondent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class MessagesFilter extends RequestFilter {

    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        try {
            String chatId = QueryString.getFirstPathClean(req.getPathInfo());
            if (chatId == null || chatId.isBlank()) {
                req.setAttribute(RequestAttribute.CHAT_ID.value(), "");
                return true;
            }
            UUID.fromString(chatId);
            req.setAttribute(RequestAttribute.CHAT_ID.value(), chatId);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        try {
            Respondent.redirect(res, ServletPath.MESSAGES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.tinder.filters;


import org.tinder.enums.RequestAttribute;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.Respondent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UsersPageFilter extends RequestFilter {

    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        int usersOffset;
        try {
            usersOffset = CookieWorker.getUsersOffset(req);
            if (usersOffset < 0) usersOffset = 0;
        } catch (RuntimeException ignored) {
            usersOffset = 0;
            CookieWorker.setUsersOffset(res, usersOffset);
        }
        req.setAttribute(RequestAttribute.USERS_OFFSET.value(), usersOffset);

        if (req.getMethod().equals("POST")) {
            String toId = req.getParameter("toId");
            String valueString = req.getParameter("value");
            if (toId == null || valueString == null) return false;
            try {
                Integer.parseInt(toId);
                int like = Integer.parseInt(valueString);
                if (like < 0 || like > 1) {
                    throw new RuntimeException("Invalid like value!");
                }
                return true;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                return false;
            }
        }

        return true;
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        try {
            Respondent.badRequest(res, "Invalid value");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

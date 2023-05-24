package org.tinder.filters;


import org.tinder.enums.RequestAttribute;
import org.tinder.enums.ServletPath;
import org.tinder.services.UserServices;
import org.tinder.utils.CookieWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends RequestFilter {

    private final UserServices userServices = new UserServices();

    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {

        try {
            Integer userId = CookieWorker.getAuth(req);
            userServices.getById(userId);
            req.setAttribute(RequestAttribute.USER_ID.value(), userId);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        try {
            res.sendRedirect(ServletPath.HOME.path());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

package org.tinder.filters;


import org.tinder.enums.RequestAttribute;
import org.tinder.enums.ServletPath;
import org.tinder.services.UserServices;
import org.tinder.utils.CookieWorker;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NonAuthFilter extends RequestFilter {

    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        try {
            CookieWorker.getAuth(req);
            return false;
        } catch (Exception ignored) {
            return true;
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

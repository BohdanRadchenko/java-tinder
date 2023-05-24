package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.enums.ServletPath;
import org.tinder.exceptions.DatabaseException;
import org.tinder.models.User;
import org.tinder.services.UserServices;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.FMTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class LoginServlet extends HttpServlet {
    private final UserServices userService = new UserServices();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HashMap<String, Object> data = new HashMap<>();
        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("login.ftl")
                    .process(data, w);
        } catch (TemplateException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ip = req.getRemoteAddr();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try {
            User user = userService.login(email, password);
            userService.updateLastLogin(user.id(), ip);
            CookieWorker.setAuth(resp, user.id());
            resp.sendRedirect(ServletPath.USERS.path());
        } catch (DatabaseException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }
}

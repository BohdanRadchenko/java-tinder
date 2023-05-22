package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.models.User;
import org.tinder.services.UserServices;
import org.tinder.utils.FMTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class UsersServlet extends HttpServlet {
    private final UserServices userServices = new UserServices();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 1;
        int offset = 0;
        
        User userForShow = userServices.getUserForShow(id, offset);

        HashMap<String, Object> data = new HashMap<>();
        data.put("user", userForShow);

        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("user.ftl")
                    .process(data, w);
        } catch (TemplateException x) {
            throw new RuntimeException(x);
        }
    }
}

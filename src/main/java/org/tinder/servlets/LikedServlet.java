package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.enums.RequestAttribute;
import org.tinder.enums.ServletPath;
import org.tinder.exceptions.NotFoundException;
import org.tinder.models.User;
import org.tinder.services.ChatServices;
import org.tinder.services.UserServices;
import org.tinder.utils.FMTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LikedServlet extends HttpServlet {
    private final UserServices userServices = new UserServices();
    private final ChatServices chatServices = new ChatServices();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getAttribute(RequestAttribute.USER_ID.value());

        List<User> likedUsers = new ArrayList<>();
        try {
            likedUsers = userServices.getLikedUsers(userId);
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }

        HashMap<String, Object> data = new HashMap<>();
        data.put("users", likedUsers);

        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("liked.ftl")
                    .process(data, w);
        } catch (TemplateException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer currentId = (Integer) req.getAttribute(RequestAttribute.USER_ID.value());
        String toIdString = req.getParameter("toId");
        try {
            int toId = Integer.parseInt(toIdString);
            if (toId <= 0) {
                throw new IllegalArgumentException("Invalid user id!");
            }
            String chatUuid = chatServices.getOrCreateChatUuidByUserId(currentId, toId);
            resp.sendRedirect(ServletPath.MESSAGES.of(chatUuid));
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException(ex);
        }
    }
}

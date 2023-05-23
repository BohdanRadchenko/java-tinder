package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.enums.RequestAttribute;
import org.tinder.exceptions.NotFoundException;
import org.tinder.models.Like;
import org.tinder.models.User;
import org.tinder.services.LikesServices;
import org.tinder.services.UserServices;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.FMTemplate;
import org.tinder.utils.Respondent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class UsersServlet extends HttpServlet {
    private final UserServices userServices = new UserServices();
    private final LikesServices likesServices = new LikesServices();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userId = (Integer) req.getAttribute(RequestAttribute.USER_ID.value());
        Integer userOffset = (Integer) req.getAttribute(RequestAttribute.USERS_OFFSET.value());

        User userForShow = null;
        try {
            userForShow = userServices.getUserForShow(userId, userOffset);
        } catch (NotFoundException ex) {
            System.out.println(ex.getMessage());
            req.setAttribute(RequestAttribute.USERS_OFFSET.value(), 0);
            CookieWorker.setUsersOffset(resp, 0);
            doGet(req, resp);
        }

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer fromId = (Integer) req.getAttribute(RequestAttribute.USER_ID.value());
        Integer toId;
        Integer value;

        try {
            String toIdString = req.getParameter("toId");
            String valueString = req.getParameter("value");
            toId = Integer.parseInt(toIdString);
            value = Integer.parseInt(valueString);
        } catch (NumberFormatException ex) {
            doGet(req, resp);
            throw new RuntimeException(ex);
        }

        Like like = new Like(null, fromId, toId, value);
        boolean hasResult = likesServices.createOrUpdate(like);

        if (hasResult) {
            Integer userOffset = (Integer) req.getAttribute(RequestAttribute.USERS_OFFSET.value()) + 1;
            CookieWorker.setUsersOffset(resp, userOffset);
            req.setAttribute(RequestAttribute.USERS_OFFSET.value(), userOffset);
            doGet(req, resp);
            return;
        }
        
        Respondent.internalError(resp);
    }
}

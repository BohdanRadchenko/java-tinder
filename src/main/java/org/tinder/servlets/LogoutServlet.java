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

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CookieWorker.logout(resp);
        resp.sendRedirect(ServletPath.LOGIN.path());
    }
}

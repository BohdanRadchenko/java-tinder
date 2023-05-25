package org.tinder.servlets;

import freemarker.template.TemplateException;
import org.tinder.enums.ServletPath;
import org.tinder.exceptions.DatabaseException;
import org.tinder.models.User;
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

public class RegisterServlet extends HttpServlet {
    private final UserServices userServices = new UserServices();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HashMap<String, Object> data = new HashMap<>();
        try (PrintWriter w = resp.getWriter()) {
            FMTemplate
                    .getTemplate("register.ftl")
                    .process(data, w);
        } catch (TemplateException x) {
            throw new RuntimeException(x);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ip = req.getRemoteAddr();
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("inputEmail");
        String profession = req.getParameter("profession");
        String password = req.getParameter("password");

        User user = User.of(email, password, profession, firstName, lastName);

        try {
            int id = userServices.register(user, ip);
            CookieWorker.setAuth(resp, id);
            CookieWorker.setUsersOffset(resp, 0);
            Respondent.redirect(resp, ServletPath.HOME);
        } catch (Exception ex) {
            Respondent.badRequest(resp);
        }

    }

}

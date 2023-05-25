package org.tinder.filters;

import org.tinder.enums.RequestAttribute;
import org.tinder.enums.ServletPath;
import org.tinder.exceptions.NotFoundException;
import org.tinder.services.UserServices;
import org.tinder.utils.CookieWorker;
import org.tinder.utils.QueryString;
import org.tinder.utils.Respondent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class RegisterFilter extends RequestFilter {

    private final UserServices userServices = new UserServices();

    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {

        if (!req.getMethod().equals("POST")) return true;

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("inputEmail");
        String profession = req.getParameter("profession");
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");

        if (email == null || email.isEmpty()
                || profession == null || profession.isEmpty()
                || firstName == null || firstName.isEmpty()
                || lastName == null || lastName.isEmpty()
                || password == null || password.isEmpty()
                || repeatPassword == null || repeatPassword.isEmpty()) {
            return false;
        }
        if (!password.equals(repeatPassword)) return false;
        try {
            userServices.getByEmail(email);
            return false;
        } catch (NotFoundException ignored) {
            return true;
        }
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        try {
            res.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

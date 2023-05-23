package org.tinder.utils;

import org.tinder.enums.ServletPath;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Respondent {
    public static void redirect(HttpServletResponse res, ServletPath path) throws IOException {
        res.sendRedirect(path.path());
    }

    public static void badRequest(HttpServletResponse res, String message) throws IOException {
        res.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
    }

    public static void internalError(HttpServletResponse res, String message) throws IOException {
        res.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, message);
    }

    public static void internalError(HttpServletResponse res) throws IOException {
        internalError(res, "Ooops... Something wrong!");
    }
}

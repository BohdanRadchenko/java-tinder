package org.tinder.utils;

import org.tinder.enums.ServletPath;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Respondent {
    public static void redirect(HttpServletResponse res, ServletPath path) throws IOException {
        res.sendRedirect(path.path());
    }
}

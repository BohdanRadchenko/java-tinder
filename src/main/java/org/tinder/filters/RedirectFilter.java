package org.tinder.filters;

import org.tinder.enums.ServletPath;
import org.tinder.utils.Respondent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectFilter extends RequestFilter {

    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        try {
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        try {
            Respondent.redirect(res, ServletPath.TEMPLATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

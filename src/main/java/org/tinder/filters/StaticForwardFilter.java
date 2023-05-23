package org.tinder.filters;

import org.tinder.enums.ServletPath;
import org.tinder.utils.QueryString;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StaticForwardFilter extends RequestFilter {
    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        boolean isStaticServlet = req.getContextPath().equals(ServletPath.STATIC.path());
        if(isStaticServlet || req.getPathInfo() == null) return true;
        return !QueryString.isStaticPath(req.getPathInfo());
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
        try {
            req.getPathInfo();
            RequestDispatcher rd = req.getRequestDispatcher(req.getPathInfo());
            if(rd == null) return;
            rd.forward(req, res);
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.tinder.filters;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter extends RequestFilter {

    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        return true;
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
    }
}

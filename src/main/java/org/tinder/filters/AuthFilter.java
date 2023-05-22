package org.tinder.filters;


import org.tinder.enums.RequestAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthFilter extends RequestFilter {

    @Override
    boolean accept(HttpServletRequest req, HttpServletResponse res) {
        req.setAttribute(RequestAttribute.USER_ID.value(), 1);
        return true;
    }

    @Override
    void failed(HttpServletRequest req, HttpServletResponse res) {
    }
}

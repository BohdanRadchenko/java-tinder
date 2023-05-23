package org.tinder.filters;

import org.tinder.interfaces.HttpFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class RequestFilter {

    abstract boolean accept(HttpServletRequest req, HttpServletResponse res);

    abstract void failed(HttpServletRequest req, HttpServletResponse res);

    public HttpFilter of() {
        return HttpFilter.create(
                this::accept,
                this::failed
        );
    }
}

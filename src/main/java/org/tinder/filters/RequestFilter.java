package org.tinder.filters;

import org.tinder.interfaces.HttpFilter;
import org.tinder.services.Services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class RequestFilter {
    public final Services services;

    public RequestFilter(Services services) {
        this.services = services;
    }

    public RequestFilter() {
        this(null);
    }

    abstract boolean accept(HttpServletRequest req, HttpServletResponse res);

    abstract void failed(HttpServletRequest req, HttpServletResponse res);

    public HttpFilter of() {
        return HttpFilter.create(
                this::accept,
                this::failed
        );
    }
}

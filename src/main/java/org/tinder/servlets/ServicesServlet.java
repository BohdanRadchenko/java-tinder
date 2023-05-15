package org.tinder.servlets;

import org.tinder.services.Services;

import javax.servlet.http.HttpServlet;

public abstract class ServicesServlet extends HttpServlet {
    public final Services services;

    public ServicesServlet(Services services) {
        this.services = services;
    }

}

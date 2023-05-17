package org.tinder;

import org.tinder.enums.ServletPath;
import org.tinder.filters.MessagesFilter;
import org.tinder.filters.RedirectFilter;
import org.tinder.filters.StaticForwardFilter;
import org.tinder.services.Services;
import org.tinder.servlets.*;
import org.tinder.utils.Config;
import org.tinder.utils.Constants;
import org.tinder.utils.Database;
import org.tinder.utils.ResourcesOps;

import javax.xml.crypto.Data;

public class TinderApplication implements Runnable {
    private final HTTPServer server;
    private final Services services;

    public TinderApplication() {
        server = new HTTPServer(Config.getPort());
        services = Services.create();
    }

    private void initDatabase() throws Exception {
        System.out.println("db connection...");
        Database.connect();

        //TODO: remove next line.
        System.out.println("work with database");

        System.out.println("db connected...");
    }

    private void initMapping() {
        // static content
        //TODO: create static servlet
        server.addFilter(new StaticForwardFilter(), ServletPath.WILDCARD);
        server.addServlet(new StaticServlet(ResourcesOps.dir(Constants.STATIC_CONTENT_DIR)), ServletPath.STATIC_WILDCARD);

        // home
        // TODO: example home servlet. Remove in development
        server.addServlet(new HomeServlet(), ServletPath.HOME);
        server.addServlet(new RedirectServlet(), ServletPath.REDIRECT, new RedirectFilter(services));
        server.addServlet(new TemplateServlet(), ServletPath.TEMPLATE);

        // auth

        // liked

        // users

        // messages
        server.addServlet(new MessagesServlet(), ServletPath.MESSAGES);
        server.addServlet(new MessagesServlet(), ServletPath.MESSAGES_WILDCARD, new MessagesFilter());
    }

    @Override
    public void run() {
        try {
            //TODO: before starting server create database connection
            initDatabase();

            initMapping();

            server.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
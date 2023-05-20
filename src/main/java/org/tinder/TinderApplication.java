package org.tinder;

import org.tinder.enums.ServletPath;
import org.tinder.filters.MessagesFilter;
import org.tinder.filters.RedirectFilter;
import org.tinder.filters.StaticForwardFilter;
import org.tinder.servlets.*;
import org.tinder.utils.Config;
import org.tinder.utils.Constants;
import org.tinder.utils.Database;
import org.tinder.utils.ResourcesOps;

public class TinderApplication implements Runnable {
    private final HTTPServer server;

    public TinderApplication() {
        server = new HTTPServer(Config.getPort());
    }

    private void initDatabase() throws Exception {
        System.out.println("db connection...");
        Database.connect();
        System.out.println("db connected...");
    }


    private void initWs() {
        System.out.println("ws init...");
        WSServer.of(server.getContext());
        System.out.println("ws configured...");
    }


    private void initMapping() {
        // static content
        //TODO: create static servlet
        server.addFilter(new StaticForwardFilter(), ServletPath.WILDCARD);
        server.addServlet(new StaticServlet(ResourcesOps.dir(Constants.STATIC_CONTENT_DIR)), ServletPath.STATIC_WILDCARD);

        // home
        // TODO: example home servlet. Remove in development
        server.addServlet(new HomeServlet(), ServletPath.HOME);
        server.addServlet(new RedirectServlet(), ServletPath.REDIRECT, new RedirectFilter());
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
            initDatabase();

            initWs();

            initMapping();

            server.start();
        } catch (Exception e) {
            Database.close();
            throw new RuntimeException(e);
        }
    }
}
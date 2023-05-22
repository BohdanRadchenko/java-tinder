package org.tinder;

import org.tinder.enums.ServletPath;
import org.tinder.filters.AuthFilter;
import org.tinder.filters.MessagesFilter;
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
        server.addFilter(ServletPath.WILDCARD, new StaticForwardFilter());
        server.addServlet(ServletPath.STATIC_WILDCARD, new StaticServlet(ResourcesOps.dir(Constants.STATIC_CONTENT_DIR)));

        // home
        // TODO: example home servlet. Remove in development
        server.addServlet(ServletPath.HOME, new HomeServlet());

        //auth
        server.addServlet(ServletPath.LOGIN, new LoginServlet());


        // users
        server.addServlet(ServletPath.USERS, new UsersServlet(), new AuthFilter());

        // messages
        server.addServlet(ServletPath.MESSAGES, new MessagesServlet(), new AuthFilter());
        server.addServlet(ServletPath.MESSAGES_WILDCARD, new MessagesServlet(), new MessagesFilter(), new AuthFilter());
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
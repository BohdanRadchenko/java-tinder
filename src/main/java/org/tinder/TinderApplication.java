package org.tinder;

import org.tinder.enums.ServletPath;
import org.tinder.filters.*;
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
        server.addFilter(ServletPath.WILDCARD, new StaticForwardFilter());
        server.addServlet(ServletPath.STATIC_WILDCARD, new StaticServlet(ResourcesOps.dir(Constants.STATIC_CONTENT_DIR)));

        // home
        // TODO: example home servlet. Remove next code
        server.addServlet(ServletPath.HOME, new HomeServlet());

        //auth
        server.addServlet(ServletPath.LOGIN, new LoginServlet(), new NonAuthFilter());
        server.addServlet(ServletPath.REGISTER, new RegisterServlet(), new NonAuthFilter(), new RegisterFilter());
        server.addServlet(ServletPath.LOGOUT, new LogoutServlet(), new AuthFilter());

        // users
        server.addServlet(ServletPath.USERS, new UsersServlet(), new AuthFilter(), new UsersPageFilter());

        // likes
        server.addServlet(ServletPath.LIKES, new LikedServlet(), new AuthFilter());

        // messages
        server.addServlet(ServletPath.MESSAGES, new MessagesServlet(), new AuthFilter(), new MessagesFilter());
        server.addServlet(ServletPath.MESSAGES_WILDCARD, new MessagesServlet(), new AuthFilter(), new MessagesFilter());
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
package org.tinder;

import org.tinder.enums.ServletPath;
import org.tinder.filters.RedirectFilter;
import org.tinder.services.Services;
import org.tinder.servlets.*;
import org.tinder.utils.Config;
import org.tinder.utils.Database;
import org.tinder.utils.ResourcesOps;

import java.sql.Connection;

public class TinderApplication implements Runnable {
    private final Database database;
    private final HTTPServer server;
    private final Services services;

    public TinderApplication() {

        database = new Database();
        server = new HTTPServer(Config.getPort());
        services = Services.create();
    }

    private void initDatabase() throws Exception {
        System.out.println("db connection...");

        //TODO: remove next line.
        System.out.println("work with database");

        System.out.println("db connected...");
    }

    private void initMapping() {
        // static content
        //TODO: create static servlet
        String osStaticLocation = ResourcesOps.dir("static");
        // home
        // TODO: example home servlet. Remove in development
        server.addServlet(new HomeServlet(), ServletPath.HOME);
        server.addServlet(new RedirectServlet(), ServletPath.REDIRECT, new RedirectFilter(services));
        server.addServlet(new TemplateServlet(), ServletPath.TEMPLATE);
        // TODO: 17.05.2023 сделать 
       server.addServlet(new LoginServlet(), ServletPath.LOGIN);
        server.addServlet(new StaticContentServlet(osStaticLocation), ServletPath.STATIC_WILDCARD);
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
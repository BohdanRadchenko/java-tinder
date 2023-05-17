package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.tinder.enums.ServletPath;
import org.tinder.filters.RequestFilter;
import org.tinder.filters.StaticForwardFilter;
import org.tinder.interfaces.HttpFilter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.util.EnumSet;

public class HTTPServer {
    private final Server server;
    private final ServletContextHandler context;

    public HTTPServer(int port) {
        server = new Server(port);
        context = new ServletContextHandler();
    }

    public void addFilter(Filter filter, String path, EnumSet<DispatcherType> dispatcherTypes) {
        context.addFilter(new FilterHolder(filter), path, dispatcherTypes);
    }

    public void addFilter(Filter filter, String path) {
        EnumSet<DispatcherType> dt = EnumSet.of(DispatcherType.REQUEST);
        addFilter(filter, path, dt);
    }

    public void addFilter(RequestFilter filter, String path) {
        addFilter(filter.of(), path);
    }

    public void addFilter(Filter filter, ServletPath servletPath) {
        addFilter(filter, servletPath.path());
    }

    public void addFilter(RequestFilter filter, ServletPath servletPath) {
        addFilter(filter.of(), servletPath);
    }

    void addServlet(HttpServlet servlet, ServletPath servletPath) {
        context.addServlet(new ServletHolder(servlet), servletPath.path());
    }

    public void addServlet(HttpServlet servlet, ServletPath servletPath, HttpFilter... filters) {
        for (HttpFilter filter : filters) {
            addFilter(filter, servletPath);
        }
        addServlet(servlet, servletPath);
    }

    public void addServlet(HttpServlet servlet, ServletPath servletPath, RequestFilter... filters) {
        for (RequestFilter filter : filters) {
            addFilter(filter, servletPath);
        }
        addServlet(servlet, servletPath);
    }

    public void start() throws Exception {
        server.setHandler(context);

        System.out.println("Server starting...");

        server.start();
        System.out.println("Server started...");
        server.join();
    }
}

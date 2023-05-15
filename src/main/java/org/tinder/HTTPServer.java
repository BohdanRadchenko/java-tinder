package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.tinder.enums.ServletPath;
import org.tinder.filters.RequestFilter;
import org.tinder.interfaces.HttpFilter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.http.HttpServlet;
import java.util.EnumSet;

public class HTTPServer {
    private final Server server;
    private final ServletContextHandler handler;

    public HTTPServer(int port) {
        server = new Server(port);
        handler = new ServletContextHandler();
    }

    public void addFilter(Filter filter, ServletPath servletPath, EnumSet<DispatcherType> dispatcherTypes) {
        handler.addFilter(new FilterHolder(filter), servletPath.path(), dispatcherTypes);
    }

    public void addFilter(Filter filter, ServletPath servletPath) {
        EnumSet<DispatcherType> dt = EnumSet.of(DispatcherType.REQUEST);
        addFilter(filter, servletPath, dt);
    }

    public void addServlet(HttpServlet servlet, ServletPath servletPath, HttpFilter... filters) {
        for (HttpFilter filter : filters) {
            addFilter(filter, servletPath);
        }
        handler.addServlet(new ServletHolder(servlet), servletPath.path());
    }

    public void addServlet(HttpServlet servlet, ServletPath servletPath, RequestFilter... filters) {
        for (RequestFilter filter : filters) {
            addFilter(filter.of(), servletPath);
        }
        handler.addServlet(new ServletHolder(servlet), servletPath.path());
    }

    public void addServlet(HttpServlet servlet, ServletPath servletPath) {
        handler.addServlet(new ServletHolder(servlet), servletPath.path());
    }

    public void start() throws Exception {
        server.setHandler(handler);

        System.out.println("Server starting...");

        server.start();
        System.out.println("Server started...");
        server.join();
    }
}

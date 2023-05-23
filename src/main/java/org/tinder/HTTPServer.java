package org.tinder;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.tinder.enums.ServletPath;
import org.tinder.filters.RequestFilter;
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

    public void addFilter(String path, Filter filter, EnumSet<DispatcherType> dispatcherTypes) {
        context.addFilter(new FilterHolder(filter), path, dispatcherTypes);
    }

    public void addFilter(String path, Filter filter) {
        EnumSet<DispatcherType> dt = EnumSet.of(DispatcherType.REQUEST);
        addFilter(path, filter, dt);
    }

    public void addFilter(String path, RequestFilter filter) {
        addFilter(path, filter.of());
    }

    public void addFilter(ServletPath servletPath, Filter filter) {
        addFilter(servletPath.path(), filter);
    }

    public void addFilter(ServletPath servletPath, RequestFilter filter) {
        addFilter(servletPath, filter.of());
    }

    void addServlet(ServletPath servletPath, HttpServlet servlet) {
        context.addServlet(new ServletHolder(servlet), servletPath.path());
    }

    public void addServlet(ServletPath servletPath, HttpServlet servlet, HttpFilter... filters) {
        for (HttpFilter filter : filters) {
            addFilter(servletPath, filter);
        }
        addServlet(servletPath, servlet);
    }

    public void addServlet(ServletPath servletPath, HttpServlet servlet, RequestFilter... filters) {
        for (RequestFilter filter : filters) {
            addFilter(servletPath, filter);
        }
        addServlet(servletPath, servlet);
    }

    public ServletContextHandler getContext() {
        return this.context;
    }

    public void start() throws Exception {
        server.setHandler(context);

        System.out.println("Server starting...");

        server.start();
        System.out.println("Server started...");
        server.join();
    }
}

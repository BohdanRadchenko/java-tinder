package org.tinder;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.javax.server.config.JavaxWebSocketServletContainerInitializer;
import org.tinder.sockets.ChatSocket;

public class WSServer {
    private final ServletContextHandler context;

    public WSServer(ServletContextHandler context) {
        this.context = context;
    }

    public void config() {
        JavaxWebSocketServletContainerInitializer.configure(context, (servletContext, wsContainer) ->
        {
            wsContainer.setDefaultMaxTextMessageBufferSize(65535);
            wsContainer.addEndpoint(ChatSocket.class);
            wsContainer.setDefaultMaxSessionIdleTimeout(3600000);
        });
    }

    public static WSServer of(ServletContextHandler context) {
        WSServer wsServer = new WSServer(context);
        wsServer.config();
        return wsServer;
    }

}

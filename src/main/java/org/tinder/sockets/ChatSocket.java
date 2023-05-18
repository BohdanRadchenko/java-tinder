package org.tinder.sockets;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
@ServerEndpoint(value = "/chats/{id}")
public class ChatSocket {
    private static final Map<String, List<Session>> sessions = new HashMap<>();
    private final CountDownLatch closureLatch = new CountDownLatch(1);

    @OnOpen
    public void onWebSocketConnect(@PathParam("id") String id, Session sess) {
        System.out.println("sessions size: "+ sessions.size());
        List<Session> ss = sessions.getOrDefault(id, new LinkedList<>());
        ss.add(sess);
        sessions.put(id, ss);
        System.out.println("WebSocket opened: " + sess.getId());
        System.out.println("Socket Connected: " + sess);
        System.out.println("sessions size: "+ sessions.size());
    }

    @OnMessage
    public void onWebSocketText(@PathParam("id") String id, Session sess, String message) throws IOException {
        System.out.println("id: " + id);
        System.out.println("Message received: " + message);

        for (Map.Entry<String, List<Session>> entry : sessions.entrySet()) {
            String key = entry.getKey();
            List<Session> value = entry.getValue();
            System.out.println(key);
            value.forEach(s -> {
                System.out.println(s.getId());
                try {
                    s.getBasicRemote().sendText(message.toUpperCase());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            });
        }

        System.out.println("Received TEXT message: " + message);

        if (message.toLowerCase(Locale.US).contains("bye")) {
            sess.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Thanks"));
        }
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());

        System.out.println("Socket Closed: " + reason);
        closureLatch.countDown();
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        cause.printStackTrace(System.err);
    }

    public void awaitClosure() throws InterruptedException {
        System.out.println("Awaiting closure from remote");
        closureLatch.await();
    }
}
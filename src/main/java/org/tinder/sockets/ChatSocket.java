package org.tinder.sockets;

import com.google.gson.Gson;
import org.tinder.servlets.MessagesServlet;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
@ServerEndpoint(value = "/chats/{id}")
public class ChatSocket {

    // TODO: 18.05.2023 draft code. remove
    private record Message(String content, String type) {
    }

    private record MessageResponse(MessagesServlet.User from, Message message) {
    }

    private static final Map<String, List<Session>> sessions = new HashMap<>();
    private final CountDownLatch closureLatch = new CountDownLatch(1);

    @OnOpen
    public void onWebSocketConnect(@PathParam("id") String id, Session sess) {
        System.out.println("WebSocket opened: " + sess.getId());
        List<Session> ss = sessions.getOrDefault(id, new LinkedList<>());
        ss.add(sess);
        sessions.put(id, ss);
    }

    @OnMessage
    public void onWebSocketText(@PathParam("id") String id, Session sess, String message) throws IOException {
        for (Session session : sessions.get(id)) {
            try {
                Gson gson = new Gson();
                MessagesServlet.User user = new MessagesServlet.User(1, "email", "name", "https://www.graphicpie.com/wp-content/uploads/2020/11/red-among-us-png-842x1024.png");
                Message msg = new Message(message.toUpperCase(), "TEXT");
                MessageResponse msgRespo = new MessageResponse(user, msg);
                session.getBasicRemote().sendText(gson.toJson(msgRespo));
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        if (message.toLowerCase(Locale.US).contains("close")) {
            sess.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Session closed"));
        }
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
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
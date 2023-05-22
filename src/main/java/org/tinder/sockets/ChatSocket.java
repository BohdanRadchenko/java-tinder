package org.tinder.sockets;

import org.tinder.entities.SocketReqMessage;
import org.tinder.entities.SocketResMessage;
import org.tinder.models.Message;
import org.tinder.services.MessageServices;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
@ServerEndpoint(value = "/chats/{id}")
public class ChatSocket {
    private final MessageServices messageServices = new MessageServices();

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
                SocketReqMessage reqMessage = SocketReqMessage.of(message);

                int insertedId = messageServices.insert(reqMessage);
                Message msg = messageServices.getById(insertedId);
                SocketResMessage res = SocketResMessage.of(msg);
                session.getBasicRemote().sendText(res.toJson());

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
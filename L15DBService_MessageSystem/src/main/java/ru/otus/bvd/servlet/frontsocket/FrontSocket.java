package ru.otus.bvd.servlet.frontsocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.Set;

@WebSocket
public class FrontSocket {
    private Session session;

    @OnWebSocketMessage
    public void onMessage(String data) {
        System.out.println("onMessage");
//        for (FrontSocket user : users) {
//            try {
//                user.getSession().getRemote().sendString(data);
//                System.out.println("Sending message: " + data);
//            } catch (Exception e) {
//                System.out.print(e.toString());
//            }
//        }
    }

    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        System.out.println("onOpen");
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        System.out.println("onClose");
    }
}

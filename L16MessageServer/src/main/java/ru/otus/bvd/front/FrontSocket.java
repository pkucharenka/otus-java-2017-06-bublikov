package ru.otus.bvd.front;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ru.otus.bvd.example.MainMessageSystem;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@WebSocket
public class FrontSocket {
    private static final AtomicInteger requestIdSeq = new AtomicInteger(0);
    private Session session;
    
    @Autowired
    private FrontendService frontService;

    @OnWebSocketMessage
    public void onMessage(String data) {
        if (frontService == null)
            frontService = MainMessageSystem.appContext.getBean(FrontendService.class);
        long requestId = requestIdSeq.getAndIncrement();
        frontService.handleRequest(Long.parseLong(data), requestId, this);
    }
    
    public void sendResponse(String data) {        
        try {
            session.getRemote().sendString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    @OnWebSocketConnect
    public void onOpen(Session session) {
        setSession(session);
        System.out.println("onOpen " + this.toString());        
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

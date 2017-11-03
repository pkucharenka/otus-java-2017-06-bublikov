package ru.otus.bvd.fs.front;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import ru.otus.bvd.ms.app.FrontSocket;
import ru.otus.bvd.ms.app.FrontendService;

@WebSocket
public class FrontSocketImpl implements FrontSocket {
    private static final AtomicInteger requestIdSeq = new AtomicInteger(0);
    private Session session;
    private FrontendService frontendService;
    
    @OnWebSocketMessage
    public void onMessage(String data) {
        if (frontendService == null)
        	frontendService = FrontendServiceImpl.getInstance();
        long requestId = requestIdSeq.getAndIncrement();
        frontendService.handleRequest(Long.parseLong(data), requestId, this);
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

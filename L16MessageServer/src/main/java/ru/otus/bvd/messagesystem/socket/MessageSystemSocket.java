package ru.otus.bvd.messagesystem.socket;

import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket
public class MessageSystemSocket {
    private static final AtomicInteger requestIdSeq = new AtomicInteger(0);
    private Session session;

}

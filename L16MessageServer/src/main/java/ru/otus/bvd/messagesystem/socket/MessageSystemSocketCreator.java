package ru.otus.bvd.messagesystem.socket;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;

import ru.otus.bvd.front.FrontSocket;

public class MessageSystemSocketCreator implements WebSocketCreator {

    @Override
    public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
        MessageSystemSocket socket = new MessageSystemSocket();
        System.out.println("Message System Socket created");
        return socket;
    }

}

package ru.otus.bvd.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;

/**
 * Created by vadim on 10.09.17.
 */
public class WebServer {
    private final Server server;
    private final static String PUBLIC_HTML = "public_html";

    public WebServer(int port) {
        server = new Server(port);
    }

    public void init() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);

        server.setHandler(resourceHandler);

    }

    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void join() {
        try {
            server.join();
        } catch (InterruptedException e) {
            Thread.currentThread().isInterrupted();
        }
    }
}

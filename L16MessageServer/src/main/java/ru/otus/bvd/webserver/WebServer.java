package ru.otus.bvd.webserver;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;

import ru.otus.bvd.front.FrontSocketServlet;
import ru.otus.bvd.servlet.AdminServlet;
import ru.otus.bvd.servlet.LoginServlet;

/**
 * Created by vadim on 10.09.17.
 */
public class WebServer {
    private final Server server;
    private static final String PUBLIC_HTML = "public_html";

    public WebServer(int port) {
        server = new Server(port);
    }

    public void init() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet( new ServletHolder(new LoginServlet("anonymous")) , "/login");
        context.addServlet( AdminServlet.class , "/admin");
        context.addServlet(FrontSocketServlet.class, "/front");        
        
//        context.addEventListener(new ContextLoaderListener());
//        context.setInitParameter("contextConfigLocation", "classpath:SpringBeans.xml");
        
        server.setHandler(new HandlerList(resourceHandler, context));
        
    }
    
    public Server getServer() {
        return server;
    }
    
    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void stop() throws Exception {
        server.stop();
    }

    public void join() {
        try {
            server.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

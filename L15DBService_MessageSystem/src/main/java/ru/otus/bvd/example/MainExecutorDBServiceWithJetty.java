package ru.otus.bvd.example;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.messagesystem.MessageSystem;
import ru.otus.bvd.webserver.WebServer;

public class MainExecutorDBServiceWithJetty {
    WebServer webServer;
    @Autowired DBService dbService;
    @Autowired MessageSystem messageSystem;
    
    public static void main(String[] args) {
        configureLog();
        
        MainExecutorDBServiceWithJetty mainInstance = new MainExecutorDBServiceWithJetty();
        mainInstance.startWebServer();
        mainInstance.initAutowiredBeans();
        mainInstance.messageSystem.start();
        mainInstance.startDBActivity();
        
    }

    private static void configureLog() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        Logger.getGlobal().setLevel(Level.INFO);
    }
    
    
    @Autowired private ApplicationContext applicationContext;
    private void startWebServer() {
        webServer = new WebServer(8090);
        webServer.init();
        webServer.start();               
    }
    
    private void initAutowiredBeans() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }
    
    private void startDBActivity() {
        Thread dbActivity = new Thread( new DBActivity(dbService));
        dbActivity.start();                
    }    
}

package ru.otus.bvd.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.front.FrontendService;
import ru.otus.bvd.messagesystem.MessageSystem;
import ru.otus.bvd.server.ServerMain;
import ru.otus.bvd.webserver.WebServer;

public class MainMessageSystem {
    private static final Logger logger = Logger.getLogger(MainMessageSystem.class.getName());
    
    public static ApplicationContext appContext;
    
    WebServer webServer;
    @Autowired DBService dbService;
    @Autowired MessageSystem messageSystem;
    @Autowired FrontendService frontendService;

    public static void main(String[] args) throws Exception {
        MainMessageSystem mainInstace = new MainMessageSystem();
        configureLog(Level.INFO);              
        
        appContext = new ClassPathXmlApplicationContext("SpringBeans.xml");
        mainInstace.initAutowiredBeans(appContext);
        mainInstace.messageSystem.start();
        mainInstace.startDBActivity();
        mainInstace.startWebServer();
    }
    
    private static void configureLog(Level level) {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        Logger.getGlobal().setLevel(level);
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        rootLogger.setLevel(level);
        for (Handler h : rootLogger.getHandlers()) {
            h.setLevel(level);
        }        
    }
    private void initAutowiredBeans(ApplicationContext context) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        dbService = context.getBean(DBService.class);
        messageSystem = context.getBean(MessageSystem.class);
        frontendService = context.getBean(FrontendService.class);
    }
    private void startDBActivity() {
        Thread dbActivity = new Thread( new DBActivity(dbService));
        dbActivity.start();                
    }
    private void startWebServer() {
        webServer = new WebServer(8090);
        webServer.init();
        webServer.start();
    }

   
}

package ru.otus.bvd.example;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.messagesystem.MessageSystem;
import ru.otus.bvd.server.ServerMain;

public class MainMessageSystem {
    private static final Logger logger = Logger.getLogger(MainMessageSystem.class.getName());
    
    @Autowired DBService dbService;
    @Autowired MessageSystem messageSystem;

    public static void main(String[] args) throws Exception {
        MainMessageSystem mainInstace = new MainMessageSystem();
        configureLog();      
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit( ()-> {
            try {
                new ServerMain().start();
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }           
        } );
        
        
        ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
        mainInstace.initAutowiredBeans(context);
        mainInstace.messageSystem.start();
        mainInstace.startDBActivity();
    }
    
    private static void configureLog() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        Logger.getGlobal().setLevel(Level.INFO);
    }
    private void initAutowiredBeans(ApplicationContext context) {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        dbService = context.getBean(DBService.class);
        messageSystem = context.getBean(MessageSystem.class);
    }
    private void startDBActivity() {
        Thread dbActivity = new Thread( new DBActivity(dbService));
        dbActivity.start();                
    }    
   
}

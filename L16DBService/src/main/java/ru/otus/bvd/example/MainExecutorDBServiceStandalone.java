package ru.otus.bvd.example;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.messagesystem.MessageSystem;

public class MainExecutorDBServiceStandalone {
    DBService dbService;
    MessageSystem messageSystem;
    
    public static void main(String[] args) {
        configureLog();
        MainExecutorDBServiceStandalone mainInstance = new MainExecutorDBServiceStandalone();

        ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
        mainInstance.dbService = (DBService) context.getBean("dbService");
        mainInstance.messageSystem = (MessageSystem) context.getBean("messageSystem");

        mainInstance.messageSystem.start();
        mainInstance.startDBActivity();
        
        
        
    }

    private static void configureLog() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        Logger.getGlobal().setLevel(Level.INFO);
    }
        
    
    private void startDBActivity() {
        Thread dbActivity = new Thread( new DBActivity(dbService));
        dbActivity.start();                
    }    
}

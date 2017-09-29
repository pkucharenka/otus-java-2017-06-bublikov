package ru.otus.bvd.example;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.webserver.WebServer;

public class MainExecutorDBServiceWithJetty {
    public static WebServer webServer;
    public static DBService dbService;
    
    public static void main(String[] args) throws InterruptedException {
        MainExecutorDBServiceWithJetty mainInstance = new MainExecutorDBServiceWithJetty();
        mainInstance.work();
        

    }
    
    private void work() {
        
        
        System.out.println(Thread.currentThread().getName());
        
        webServer = new WebServer(8080);
        webServer.init();
        webServer.start();        
    }
    
    
    
}

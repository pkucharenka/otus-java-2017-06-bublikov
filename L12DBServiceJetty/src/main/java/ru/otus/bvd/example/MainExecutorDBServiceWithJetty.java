package ru.otus.bvd.example;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.base.DBServiceImpl;
import ru.otus.bvd.dataset.UserDataSet;
import ru.otus.bvd.webserver.WebServer;

public class MainExecutorDBServiceWithJetty {
    public static void main(String[] args) throws InterruptedException {
//        DBService dbService = new DBServiceImpl();
//
//        UserDataSet userDataSetS = new UserDataSet("Bublikov Vadim Dmitrievich", 35, null);
//        dbService.save(userDataSetS);
//        System.out.println("user to db  : " + userDataSetS.toString());
//        
//        UserDataSet userDataSetR = dbService.read(1);
//        System.out.println("user from db: " + userDataSetR.toString());  
//
//        userDataSetR = dbService.read(1);
//        System.out.println("user from db: " + userDataSetR.toString());  
//                
//        //example life time
//        long timeEndLife = System.currentTimeMillis() + 10100;
//        while (System.currentTimeMillis() < timeEndLife) {
//            userDataSetR = dbService.read(1);      
//            Thread.sleep(500);
//        }
//        
//        //example idle time
//        Thread.sleep(1000);
//        userDataSetR = dbService.read(1);

        WebServer webServer = new WebServer(8090);
        webServer.init();
        webServer.start();

//        Thread.sleep(20000);
//
//        webServer.stop();
//        dbService.shutdown();        
    }    
}

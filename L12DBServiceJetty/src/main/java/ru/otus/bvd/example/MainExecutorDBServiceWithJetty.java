package ru.otus.bvd.example;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.base.DBServiceImpl;
import ru.otus.bvd.dataset.UserDataSet;
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
        
        dbService = new DBServiceImpl();
        
        Thread dbActivity = new Thread( new DBActivity());
        dbActivity.start();

        webServer = new WebServer(8090);
        webServer.init();
        webServer.start();        
    }
    
    
    public static class DBActivity implements Runnable {
        @Override
        public void run() {
          while (!Thread.currentThread().isInterrupted()) {
              System.out.println(Thread.currentThread().getName());
              try {
                  UserDataSet userDataSetS = new UserDataSet("Bublikov Vadim Dmitrievich", 35, null);
                  dbService.save(userDataSetS);
                  System.out.println("user to db  : " + userDataSetS.toString());
                  
                  UserDataSet userDataSetR = dbService.read(1);
                  System.out.println("user from db: " + userDataSetR.toString());  
          
                  userDataSetR = dbService.read(1);
                  System.out.println("user from db: " + userDataSetR.toString());  
                          
                  //example life time
                  long timeEndLife = System.currentTimeMillis() + 10100;                  
                  while (System.currentTimeMillis() < timeEndLife && !Thread.currentThread().isInterrupted()) {
                      userDataSetR = dbService.read(1);      
                      Thread.sleep(500);
                  }
                  
                  //example idle time
                  if (!Thread.currentThread().isInterrupted())
                      Thread.sleep(1000);
                  userDataSetR = dbService.read(1);              
              } catch (InterruptedException e) {
                  Thread.currentThread().interrupted();
              }              
          }                        
        }
    }
    
}

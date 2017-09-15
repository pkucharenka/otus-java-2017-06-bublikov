package ru.otus.bvd.example;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.dataset.UserDataSet;

public class DBActivity implements Runnable {
    private final DBService dbService;
    
    public DBActivity(DBService dbService) {
        this.dbService = dbService;
    }
    
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

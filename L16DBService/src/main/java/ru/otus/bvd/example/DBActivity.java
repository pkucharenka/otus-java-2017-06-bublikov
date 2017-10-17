package ru.otus.bvd.example;

import java.util.Random;
import static java.util.logging.Level.*;
import java.util.logging.Logger;

import ru.otus.bvd.app.DBService;
import ru.otus.bvd.dataset.UserDataSet;

public class DBActivity implements Runnable {
    private static final Logger log = Logger.getLogger(DBActivity.class.getName());
    
    private final DBService dbService;
    
    public DBActivity(DBService dbService) {
        this.dbService = dbService;
    }
    
    private String[] names = new String[] {"Aleksey","Dmitriy","Ilya"};
    private String getRandomName() {
        Random random = new Random();
        return names[random.nextInt(3)] + " " + names[random.nextInt(3)] + " " + names[random.nextInt(3)]; 
    }
    private int getRandomAge() {
        Random random = new Random();
        return random.nextInt(100);
    }
    
    @Override
    public void run() {
      while (!Thread.currentThread().isInterrupted()) {
          log.info(Thread.currentThread().getName());
          try {
              UserDataSet userDataSetS = new UserDataSet(getRandomName(), getRandomAge(), null);
              dbService.save(userDataSetS);
              log.info("user to db  : " + userDataSetS.toString());
              
              UserDataSet userDataSetR = dbService.read(1);
              log.info("user from db: " + userDataSetR.toString());
      
              userDataSetR = dbService.read(1);
              log.info("user from db: " + userDataSetR.toString());
                      
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
              Thread.currentThread().interrupt();
          }              
      }                        
    }
}

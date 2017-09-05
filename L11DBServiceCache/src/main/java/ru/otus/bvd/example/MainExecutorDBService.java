package ru.otus.bvd.example;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.base.DBServiceImpl;
import ru.otus.bvd.dataset.UserDataSet;

public class MainExecutorDBService {
    public static void main(String[] args) throws InterruptedException {
        DBService dbService = new DBServiceImpl();

        UserDataSet userDataSetS = new UserDataSet("Bublikov Vadim Dmitrievich", 35, null);
        dbService.save(userDataSetS);
        System.out.println("user to db  : " + userDataSetS.toString());
        
        UserDataSet userDataSetR = dbService.read(1);
        System.out.println("user from db: " + userDataSetR.toString());  

        userDataSetR = dbService.read(1);
        System.out.println("user from db: " + userDataSetR.toString());  
                
        //example life time
        long timeEndLife = System.currentTimeMillis() + 10100;
        while (System.currentTimeMillis() < timeEndLife) {
            userDataSetR = dbService.read(1);      
            Thread.sleep(500);
        }
        
        //example idle time
        Thread.sleep(1000);
        userDataSetR = dbService.read(1);
        
        //example soft references see in CacheMain
        
        
        dbService.shutdown();        
    }    
}

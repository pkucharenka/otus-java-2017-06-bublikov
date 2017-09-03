package ru.otus.bvd.example;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.base.DBServiceImpl;
import ru.otus.bvd.dataset.PhoneDataSet;
import ru.otus.bvd.dataset.UserDataSet;

public class MainExecutorDBService {
    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl();

        UserDataSet userDataSetS = new UserDataSet("Bublikov Vadim Dmitrievich", 35, null);
        dbService.save(userDataSetS);
        System.out.println("user to db  : " + userDataSetS.toString());
        
        UserDataSet userDataSetR = dbService.read(1);
        System.out.println("user from db: " + userDataSetR.toString());  
        
        dbService.shutdown();
    }
}

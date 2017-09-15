package ru.otus.bvd.example;

import ru.otus.bvd.dataset.UserDataSet;
import ru.otus.bvd.db.Database;
import ru.otus.bvd.executor.DataSetExecutor;

public class MainExecutor {
    public static void main(String[] args) {
        Database database = null;
        try {
            database = new Database();
            database.init();
            database.createScheme();
            
            UserDataSet user = new UserDataSet("Bublikov Vadim Dmitrievich", 35, null);        
            System.out.println("user to db  : " + user.toString());
            
            DataSetExecutor dsExecutor = new DataSetExecutor(database.getConnection());
            dsExecutor.save(user);
            
            user = dsExecutor.load(1, UserDataSet.class);
            System.out.println("user from db: " + user.toString());        
            
        } finally {
            if (database!=null)
                database.shutdown();
        }
    }    
}

package ru.otus.bvd.example;

import java.util.ArrayList;
import java.util.List;

import org.h2.tools.Server;

import ru.otus.bvd.base.DBService;
import ru.otus.bvd.base.DBServiceHibernateImpl;
import ru.otus.bvd.dataset.AddressDataSet;
import ru.otus.bvd.dataset.PhoneDataSet;
import ru.otus.bvd.dataset.UserDataSet;


public class MainHibernetDBService {
    public static void main(String[] args) {

        try {
            Server.createTcpServer().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        DBService dbService = null;
        try  {
            dbService = new DBServiceHibernateImpl();
    
            String status = dbService.getLocalStatus();
            System.out.println("Status: " + status);
                
            UserDataSet userOlja = new UserDataSet("olja", 33 , new AddressDataSet("Truda",23) );
            userOlja.getPhones().add( new PhoneDataSet("123", userOlja) );
            userOlja.getPhones().add( new PhoneDataSet("456", userOlja) );
            dbService.save(userOlja);
            
            UserDataSet userKolja = new UserDataSet("kolja", 44, new AddressDataSet("Mira",44) );
            userKolja.getPhones().add( new PhoneDataSet("777", userKolja) );
            userKolja.getPhones().add( new PhoneDataSet("888", userKolja) );
            userKolja.getPhones().add( new PhoneDataSet("222-3333", userKolja) );
            dbService.save(userKolja);
    
            UserDataSet dataSet = dbService.read(1);
            System.out.println("USER FROM DB = " + dataSet);
    
            dataSet = dbService.readByName("kolja");
            System.out.println("USER FROM DB = " + dataSet);
    
            List<UserDataSet> dataSets = dbService.readAll();
            for (UserDataSet userDataSet : dataSets) {
                System.out.println("LIST FROM DB = " + userDataSet);
            }
        
        } finally {   
            if (dbService!=null)
                dbService.shutdown(); 
            try {
                Server.shutdownTcpServer("tcp://localhost", "", true, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        

    }
}

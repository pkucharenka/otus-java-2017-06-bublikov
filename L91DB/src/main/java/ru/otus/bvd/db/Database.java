package ru.otus.bvd.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.Driver;

public class Database {
    private static final String DB_CONNECTION_STRING = "jdbc:h2:mem:otus;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";	
    
    Driver driver;

    public Database() {
    	driver = new Driver();    	
    }
    
    public void init() {
    	try {
			DriverManager.registerDriver(driver);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public Connection getConnection() {
    	Connection connection = null;
    	try {
			connection = DriverManager.getConnection(DB_CONNECTION_STRING, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return connection;
    }
    
}

package ru.otus.bvd.db;

import java.sql.*;

import org.h2.Driver;
import org.h2.tools.Console;

public class Database {
    private static final String DB_CONNECTION_STRING = "jdbc:h2:./baza;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "otus";
    private static final String DB_PASSWORD = "otus";
    private static final String DDL_TABLE_USER = "CREATE TABLE Z_USER (\n" +
            "    ID IDENTITY PRIMARY KEY NOT NULL, \n" +
            "    NAME VARCHAR(255), \n" +
            "    AGE  INT(3) NOT NULL DEFAULT 0\n" +
            ");\n";
    
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

    public void createScheme () {
		//create table z#user
        boolean tableExist=true;
        try (Connection connection = getConnection();
			 ResultSet rs = connection.getMetaData().getTables(null, null, "Z_USER", null);
		) {
            if (!rs.next()) {
                tableExist = false;
            } else {
                System.out.println("table Z_USER already exists");
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        if (!tableExist) {
            try (Connection connection = getConnection();
                 Statement stmnt = connection.createStatement()
            ) {
                stmnt.execute(DDL_TABLE_USER);
                System.out.println("table Z_USER created");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void main(String[] args) {
		try {
			Console.main(); //процесс продолжает выполняться
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
}

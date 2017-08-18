package ru.otus.bvd;

import java.sql.Connection;

import ru.otus.bvd.db.Database;

public class Main {
	public static void main(String[] args) {
		Database database = new Database();
		database.init();

		
		Connection dbConnection = database.getConnection();
	}
}

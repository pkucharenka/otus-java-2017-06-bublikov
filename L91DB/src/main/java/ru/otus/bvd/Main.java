package ru.otus.bvd;

import ru.otus.bvd.dataset.UsersDataSet;
import ru.otus.bvd.dataset.UsersDataSetBinding;
import ru.otus.bvd.db.Database;
import ru.otus.bvd.executor.DataSetExecutor;

public class Main {
	public static void main(String[] args) {
		Database database = new Database();
		database.init();
		database.createScheme();
		
		UsersDataSet user = new UsersDataSet("Bublikov Vadim Dmitrievich", 35);		
		System.out.println("user to db  : " + user.toString());
		UsersDataSetBinding userBinding = new UsersDataSetBinding();
		
		DataSetExecutor dsExecutor = new DataSetExecutor(database.getConnection(), userBinding);
		dsExecutor.save(user);
		
		user = dsExecutor.load(1, UsersDataSet.class);
		System.out.println("user from db: " + user.toString());
		
	}
}

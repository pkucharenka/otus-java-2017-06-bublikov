package ru.otus.bvd;

import ru.otus.bvd.department.Console;
import ru.otus.bvd.department.Department;

public class MainDepartmnet {
	public static void main (String[] args) {
		Department department = new Department();
		Console console = new Console(department);
		console.start();
	}	
}

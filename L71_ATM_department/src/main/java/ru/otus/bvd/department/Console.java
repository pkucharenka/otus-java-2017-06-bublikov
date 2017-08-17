package ru.otus.bvd.department;

import java.util.Scanner;

public class Console {
	private final Department department;
	
	public Console(Department department) {
		this.department = department;
	}
	
	public void start() {
		System.out.println("Welcome to department app!\n"
				+ "commands:\n"
				+ " a - add ATM\n"
				+ " b - print balance all ATM\n"
				+ " i - initial all ATM\n"
				+ " q - quit");
		
		String command = "";
		while (!"q".equals(command)) {
			System.out.print("Enter command: ");
			Scanner in = new Scanner(System.in);
			command = in.nextLine();
			switch (command) {
				case "a": department.addATM(); break;
				case "b": department.printBalance(); break;
				case "i": department.initialAllATM(); break;	
				case "q": continue;
				default :
					System.out.println("Incorrect command");
					break;
			}	
		}		
	}

}

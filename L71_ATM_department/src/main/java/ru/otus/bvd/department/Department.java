package ru.otus.bvd.department;

import java.util.Scanner;

public class Department {
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
				case "a": addATM(); break;
				case "b": printBalance(); break;
				case "i": initialAllATM(); break;	
				case "q": continue;
				default :
					System.out.println("Incorrect command");
					break;
			}	
		}		
	}
	
	private static void initialAllATM() {
		// TODO Auto-generated method stub
		
	}

	private static void printBalance() {
		// TODO Auto-generated method stub
		
	}

	private static void addATM() {
		// TODO Auto-generated method stub
		
	}
	
	
}

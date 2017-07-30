package ru.otus.bvd.department;

import ru.otus.bvd.atm.ATM;
import ru.otus.bvd.atm.ATMBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Department {
	List<ATM> atmList = new ArrayList<>();


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
	
	private void initialAllATM() {
		for (ATM atm : atmList) {
			CommandInitial commandInitial = new CommandInitial();
			commandInitial.addCashCount(1,10);
			commandInitial.addCashCount(5,10);
			commandInitial.addCashCount(10,10);
			commandInitial.addCashCount(25,10);
			commandInitial.addCashCount(50,10);
			atm.initial(commandInitial);
			System.out.println("initial complete");
		}
		
	}

	private void printBalance() {
		for (ATM atm : atmList) {
			System.out.println(atm.getBalance());
		}
	}

	private void addATM() {
		ATMBuilder atmBuilder = new ATMBuilder();
		atmBuilder.createATM().buildBoxMoney().buildCashIn().buildDispenser().buildPrinter().buildScreen();

		ATM atm = atmBuilder.getAtm();
		atmList.add(atm);
		System.out.println("ATM #" + atmList.indexOf(atm) + " was added");
	}
	
	
}

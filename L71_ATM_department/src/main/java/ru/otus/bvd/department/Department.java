package ru.otus.bvd.department;

import ru.otus.bvd.atm.ATM;
import ru.otus.bvd.atm.ATMBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Department {
	List<ATM> atmList = new ArrayList<>();
	
	void initialAllATM() {
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

	void printBalance() {
		for (ATM atm : atmList) {
			System.out.println(atm.getBalance());
		}
	}

	void addATM() {
		ATMBuilder atmBuilder = new ATMBuilder();
		atmBuilder.createATM().buildBoxMoney().buildCashIn().buildDispenser().buildPrinter().buildScreen();

		ATM atm = atmBuilder.getAtm();
		atmList.add(atm);
		System.out.println("ATM #" + atmList.indexOf(atm) + " was added");
	}
	
	
}

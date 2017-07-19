package ru.otus.bvd;

import ru.otus.bvd.atm.ATM;
import ru.otus.bvd.atm.ATMBuilder;
import ru.otus.bvd.atm.CashInsufficientException;
import ru.otus.bvd.bank.Banknote;

import java.util.Random;

/**
 * Created by vadim on 16.07.17.
 */
public class Main {
    public static void main(String[] args) {
        ATMBuilder atmBuilder = new ATMBuilder();
        atmBuilder.createATM().buildBoxMoney().buildCashIn().buildDispenser().buildPrinter().buildScreen();

        ATM atm = atmBuilder.getAtm();

        Random random = new Random();
        
        Banknote[] banknotesIn = new Banknote[random.nextInt(40)+1];
        for (int i=0; i<banknotesIn.length; i++)
        	banknotesIn[i]=new Banknote( randomValue() );
        
        atm.cashIn(banknotesIn);
        atm.printBalance( atm.getScreen() );

        
        
        try {
			atm.dispense(30000);
		} catch (CashInsufficientException e) {
			e.printStackTrace();
		}
        atm.printBalance(atm.getScreen() );
    }


    static int randomValue() {
        Random random = new Random();
        int value = random.nextInt(3);
        switch (value) {
            case 0:
                return 100;
            case 1:
                return 500;
            case 2:
                return 1000;
            case 3:
                return 5000;
        }
        return 0;
    }

}

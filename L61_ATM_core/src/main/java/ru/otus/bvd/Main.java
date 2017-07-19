package ru.otus.bvd;

import ru.otus.bvd.atm.ATM;
import ru.otus.bvd.atm.ATMBuilder;
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

        atm.cashIn( new Banknote[] {new Banknote(randomValue()),
                new Banknote(randomValue()), new Banknote(randomValue()), new Banknote(randomValue()), new Banknote(randomValue()) } );
        atm.printBalance( atm.getScreen() );

        atm.dispense(1500);
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

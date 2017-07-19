package ru.otus.bvd.atm;

import ru.otus.bvd.atm.ru.otus.bvd.atm.part.*;
import ru.otus.bvd.bank.Banknote;

/**
 * Created by vadim on 16.07.17.
 */
public class ATM {
    BoxCash box100;
    BoxCash box500;
    BoxCash box1000;
    BoxCash box5000;

    CashIn cashIn;
    Dispenser dispenser;

    Screen screen;



    public void cashIn (Banknote[] banknotes) {
    	cashIn.validate(banknotes);
    	cashIn.toBox(banknotes, 100, box100);
    	cashIn.toBox(banknotes, 500, box500);
    	cashIn.toBox(banknotes, 1000, box1000);
    	cashIn.toBox(banknotes, 5000, box5000);
    };

    public void dispense (int sum) throws CashInsufficientException {
    	if (sum>getBalance())
    		throw new CashInsufficientException();
    };

    public void printBalance (Printable distance) {
        distance.print( "Баланс банкомата: " + getBalance() );
    }

    private int getBalance() {
        return ( (box100.countBanknote*100)+(box500.countBanknote*500)+(box1000.countBanknote*1000)+(box5000.countBanknote*5000) );
    }

    public Screen getScreen() {
        return screen;
    }

}

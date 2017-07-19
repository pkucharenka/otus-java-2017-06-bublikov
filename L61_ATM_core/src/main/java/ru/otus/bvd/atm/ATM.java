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



    public void cashIn (Banknote[] banknotes) {};

    public void dispense (int Sum) {};

    public void printBalance (Printable distance) {
        distance.print( "Баланс банкомата: " + getBalance() );
    }

    private int getBalance() {
        return 0;
    }

    public Screen getScreen() {
        return screen;
    }

}

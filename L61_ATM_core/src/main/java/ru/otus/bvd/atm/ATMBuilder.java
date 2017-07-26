package ru.otus.bvd.atm;

import ru.otus.bvd.atm.part.BoxCash;
import ru.otus.bvd.atm.part.CashIn;
import ru.otus.bvd.atm.part.Dispenser;
import ru.otus.bvd.atm.part.Screen;

/**
 * Created by vadim on 16.07.17.
 */
public class ATMBuilder {
    private ATM atm;

    public ATMBuilder createATM() {
        atm = new ATM();
        return this;
    };
    public ATMBuilder buildBoxMoney() {
        atm.boxesCash.add(new BoxCash(atm,50));
        atm.boxesCash.add(new BoxCash(atm,25));
        atm.boxesCash.add(new BoxCash(atm,10));
        atm.boxesCash.add(new BoxCash(atm,5));
        atm.boxesCash.add(new BoxCash(atm,1));
        return this;
    };
    public ATMBuilder buildCashIn() {
        atm.cashIn = new CashIn(atm);
        return this;
    }
    public ATMBuilder buildDispenser() {
        atm.dispenser = new Dispenser(atm);
        return this;
    }
    public ATMBuilder buildScreen() {
        atm.screen = new Screen(atm);
        return this;
    }
    public ATMBuilder buildPrinter() {
        return this;
    }
    public ATM getAtm() {
        return atm;
    }
}
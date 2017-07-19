package ru.otus.bvd.atm.ru.otus.bvd.atm.part;

import ru.otus.bvd.atm.ATM;
import ru.otus.bvd.bank.Banknote;

/**
 * Created by vadim on 16.07.17.
 */
public class CashIn {
    ATM atm;

    public CashIn(ATM atm) {
        this.atm = atm;
    }

    boolean validate (Banknote[] banknotes) {
        return true;
    }

    void toBox (Banknote[] banknotes) {

    }
}

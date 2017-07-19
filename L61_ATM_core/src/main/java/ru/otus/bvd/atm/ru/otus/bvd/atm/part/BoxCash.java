package ru.otus.bvd.atm.ru.otus.bvd.atm.part;

import ru.otus.bvd.atm.ATM;

/**
 * Created by vadim on 16.07.17.
 */
public class BoxCash {
    public BoxCash(ATM atm) {
        this.atm = atm;
    }

    ATM atm;

    public int countBanknote;
}

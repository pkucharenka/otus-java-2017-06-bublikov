package ru.otus.bvd.atm.ru.otus.bvd.atm.part;

import ru.otus.bvd.atm.ATM;

/**
 * Created by vadim on 16.07.17.
 */
public class Screen implements Printable  {
    ATM atm;

    public Screen(ATM atm) {
        this.atm = atm;
    }


    public void print(String msg) {
        System.out.println(msg);
    }
}

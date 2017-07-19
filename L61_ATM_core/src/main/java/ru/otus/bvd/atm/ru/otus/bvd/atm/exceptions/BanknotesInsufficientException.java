package ru.otus.bvd.atm.ru.otus.bvd.atm.exceptions;

/**
 * Created by vadim on 19.07.17.
 */
public class BanknotesInsufficientException extends Exception {
    public BanknotesInsufficientException(int x) {
        super("Сумма должна быть кратной " + x);
    }
}

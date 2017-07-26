package ru.otus.bvd.atm.exceptions;

/**
 * Created by vadim on 19.07.17.
 */
public class BanknotesInsufficientException extends Exception {
	private static final long serialVersionUID = -5671554693192180199L;

	public BanknotesInsufficientException(int x) {
        super("Сумма должна быть кратной " + x);
    }
}

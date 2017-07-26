package ru.otus.bvd.atm.exceptions;

public class CashInsufficientException extends Exception {
	private static final long serialVersionUID = -4407680684376973974L;

	public CashInsufficientException(int sum, int balance) {
		super("Недостаточно средств. Запрошено " + sum + ", баланс " + balance);
	}
}

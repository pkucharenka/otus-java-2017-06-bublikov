package ru.otus.bvd.atm.ru.otus.bvd.atm.exceptions;

public class CashInsufficientException extends Exception {
	public CashInsufficientException(int sum, int balance) {
		super("Недостаточно средств. Запрошено " + sum + ", баланс " + balance);
	}
}

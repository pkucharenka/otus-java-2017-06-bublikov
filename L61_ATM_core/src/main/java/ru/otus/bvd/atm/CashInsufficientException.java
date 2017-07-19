package ru.otus.bvd.atm;

public class CashInsufficientException extends Exception {
	public CashInsufficientException() {
		super("Недостаточно средств");
	}
}

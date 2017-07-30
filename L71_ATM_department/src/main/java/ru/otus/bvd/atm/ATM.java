package ru.otus.bvd.atm;

import ru.otus.bvd.atm.exceptions.CashInsufficientException;
import ru.otus.bvd.atm.part.*;
import ru.otus.bvd.bank.Banknote;
import ru.otus.bvd.department.CommandInitial;

import java.util.*;

/**
 * Created by vadim on 16.07.17.
 */
public class ATM {
    List<BoxCash> boxesCash = new LinkedList<>();

    CashIn cashIn;
    Dispenser dispenser;

    Screen screen;


    public void initial(CommandInitial commandInitial) {
    	for (BoxCash box : boxesCash) {
            box.countBanknote = commandInitial.getBoxesCashCount().get(box.value);
    	}
        System.out.println("Выполнена инициация банкомата. Текущий баланс банкомата " + getBalance() + " " + boxState());
    }
    
    public void cashIn (Banknote[] banknotes) {
        int sum = cashIn.validate(banknotes);
        System.out.println("Запрошено пополнение на сумму " + sum + " Текущий баланс банкомата " + getBalance() + " " + boxState());
        for(BoxCash box: boxesCash) {
            cashIn.toBox(banknotes, box);
        }
    };

    public void dispense (int sum) throws Exception {
        System.out.println("Запрошено снятие на сумму " + sum + " Текущий баланс банкомата " + getBalance() + " " + boxState());
        if (sum>getBalance())
    		throw new CashInsufficientException(sum, getBalance());
    	Map<BoxCash, Integer> needs = new HashMap<>();

        int ost = sum;
    	for (BoxCash box : boxesCash) {
    	    if (ost>=box.value && box.countBanknote>0) {
    	        needs.put(box, Math.min(ost/box.value, box.countBanknote) );
    	        ost = ost - needs.get(box)*box.value;
            }
        }
        if (ost>0) {
            throw new Exception("Нет банкнот для выдачи суммы " + ost);
        }

        for (BoxCash box: boxesCash) {
    	    if (needs.get(box)!=null)
    	        box.countBanknote = box.countBanknote - needs.get(box);
        }
    };

    public void printBalance (Printable distance) {
        distance.print( "Баланс банкомата: " + getBalance() );
    }

    public int getBalance() {
        int balance = 0;
        for (BoxCash box : boxesCash) {
            balance = balance + box.value*box.countBanknote;
        }
        return balance;
    }

    public Screen getScreen() {
        return screen;
    }

    public String boxState() {
        StringBuilder sb = new StringBuilder();
        for (BoxCash box : boxesCash) {
            sb.append("box").append(box.value).append("=").append(box.countBanknote).append(" ");
        }
        sb.setLength(sb.length()-1);
        return "{" + sb.toString() + "}";
    }
}

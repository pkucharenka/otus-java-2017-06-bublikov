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

    public int validate (Banknote[] banknotes) {
        int sum = 0;
        for (Banknote banknote : banknotes) {
            sum = sum + banknote.value;
        }
        return sum;
    }

    public void toBox (Banknote[] banknotes, BoxCash boxCash) {
    	for (Banknote bn : banknotes) {
    		if (bn.value==boxCash.value) {
    			boxCash.countBanknote++;
    		}
    	}
    }
}

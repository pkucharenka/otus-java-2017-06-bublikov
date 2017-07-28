package ru.otus.bvd;

import ru.otus.bvd.atm.ATM;
import ru.otus.bvd.atm.ATMBuilder;
import ru.otus.bvd.atm.exceptions.BanknotesInsufficientException;
import ru.otus.bvd.atm.exceptions.CashInsufficientException;
import ru.otus.bvd.bank.Banknote;

import java.util.Random;

/**
 * Created by vadim on 16.07.17.
 */
public class MainATM {
    public static void main(String[] args) {
        ATMBuilder atmBuilder = new ATMBuilder();
        atmBuilder.createATM().buildBoxMoney().buildCashIn().buildDispenser().buildPrinter().buildScreen();

        ATM atm = atmBuilder.getAtm();
        atm.initial();

        plusCash(atm, 3);
        minusCash(atm);

        atm.printBalance(atm.getScreen() );
    }


    static void plusCash (ATM atm, int l) {
        Random random = new Random();
        for(int k=0; k<l; k++) {
            Banknote[] banknotesIn = new Banknote[random.nextInt(40) + 1];
            for (int i = 0; i < banknotesIn.length; i++)
                banknotesIn[i] = new Banknote(randomValue());
            atm.cashIn(banknotesIn);
            atm.printBalance(atm.getScreen());
        }
    }

    static void minusCash(ATM atm) {
        Random random = new Random();
        while (atm.getBalance() > 0) {
            try {
                atm.dispense(random.nextInt(100));
            } catch (CashInsufficientException e) {
                System.out.println(e.getMessage());
                ;
            } catch (BanknotesInsufficientException e) {
                System.out.println(e.getMessage());
                ;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                ;
            }
        }
    }

    static int randomValue() {
        Random random = new Random();
        int value = random.nextInt(4);
        switch (value) {
            case 0:
                return 1;
            case 1:
                return 5;
            case 2:
                return 10;
            case 3:
                return 25;
            case 4:
                return 50;
        }
        return 0;
    }

}

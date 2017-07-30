package ru.otus.bvd.department;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vadim on 30.07.17.
 */
public class CommandInitial {
    private Map<Integer,Integer> boxesCashCount = new HashMap<>();

    public void addCashCount (int value, int countBanknote) {
        boxesCashCount.put(value, countBanknote);
    }

    public Map<Integer, Integer> getBoxesCashCount() {
        return boxesCashCount;
    }
}

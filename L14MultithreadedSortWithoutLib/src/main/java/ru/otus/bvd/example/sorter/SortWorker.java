package ru.otus.bvd.example.sorter;

import java.util.Arrays;

public class SortWorker extends Thread {
    private int[] dataChunk;
    
    @Override
    public void run() {
        super.run();
        Arrays.sort(dataChunk);        
    }
    
    void setUnSortedData(int[] data) {
        this.dataChunk = data;
    }
    
    private int currentPosition = 0;
    int getCurrentElement() {
        return dataChunk[currentPosition];
    }
    void currentPositionIncrement() {
        currentPosition++;
    }
    
    boolean hasNext() {
        return currentPosition<dataChunk.length;
    }
}

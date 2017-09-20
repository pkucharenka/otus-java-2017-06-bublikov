package ru.otus.bvd.example.sorter;

import java.util.Arrays;

public class Sorter {
    private final int countThread;
    private final SortWorker[] threadPool;
    private int[] data;
    private Thread splitThread;
    
    public Sorter(int countThread) {
        this.countThread = countThread;
        this.threadPool = new SortWorker[countThread];
        for (int i = 0; i < threadPool.length; i++) {
            threadPool[i] = new SortWorker();
        }
    }
    
    public void setUnsortedData(int[] data) {
        this.data = data;
        
        
    }
    public int[] getSortedData() {
        return data;
    }
    
    public void schedule() {
        splitThread = new Thread( () -> {
            int chunkSize = data.length/threadPool.length;            
            for (int i = 0; i < threadPool.length; i++) {
                threadPool[i].setUnSortedData( Arrays.copyOfRange(data, chunkSize * i, chunkSize * (i+1)) );
                threadPool[i].start();
            }    
            for (int i = 0; i < threadPool.length; i++) {
                try {
                    threadPool[i].join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < data.length; i++) {
                data[i] = Integer.MAX_VALUE;
                int selectedChunk=-1;
                for (int t = 0; t < threadPool.length; t++) {                    
                    if (threadPool[t].hasNext() &&threadPool[t].getCurrentElement()<data[i]) {
                        data[i] = threadPool[t].getCurrentElement();
                        selectedChunk = t;
                    }
                }
                if (selectedChunk == -1) {
                    System.out.println("Здесь нужно что-то делать");
                } else {
                    threadPool[selectedChunk].currentPositionIncrement();
                }
            }
        });

        
        splitThread.start();
        
    }
    
    public void join() throws InterruptedException {
        splitThread.join();
    }
    
    
}

package ru.otus.bvd.example.sorter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sorter {
    private static final Logger log = Logger.getLogger(Sorter.class.getName());
    
    private final SortWorker[] workers;
    private int[] data;
    private Thread splitThread;
    
    public Sorter(int countThread) {
        this.workers = new SortWorker[countThread];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new SortWorker();
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
            Thread currentThread = Thread.currentThread();
            int dataLength = data.length;
            
            double chunkSizeDouble = ( (double) dataLength)/workers.length;  
            int chunkSize = BigDecimal.valueOf((chunkSizeDouble)).setScale(0, RoundingMode.UP).intValueExact();            
            
            if (log.isLoggable(Level.CONFIG)) log.info("Chunk size = " + chunkSize);
            for (int i = 0; i < workers.length; i++) {                
                int[] chunkData = Arrays.copyOfRange(data, chunkSize * i, Math.min(chunkSize*(i+1),dataLength));
                if (log.isLoggable(Level.CONFIG)) log.config(Arrays.toString(chunkData));
                workers[i].setUnSortedData( chunkData );
                workers[i].start();
            }    
            for (int i = 0; i < workers.length && !currentThread.isInterrupted() ; i++) {
                try {
                    workers[i].join();
                } catch (InterruptedException e) {
                    currentThread.interrupt();
                }
            }
            if (currentThread.isInterrupted())
                return;
            for (int i = 0; i < dataLength ; i++) {
                int minElement = Integer.MAX_VALUE;
                int selectedChunk=-1;
                for (int t = 0; t < workers.length; t++) {                    
                    if (workers[t].hasNext() &&workers[t].getCurrentElement()<minElement) {
                        minElement = workers[t].getCurrentElement();
                        selectedChunk = t;
                    }
                }
                data[i] = minElement;
                if (selectedChunk != -1)
                    workers[selectedChunk].currentPositionIncrement();
            }
        });
        
        splitThread.start();        
    }
        
    public void join() throws InterruptedException {
        splitThread.join();
    }
    
}

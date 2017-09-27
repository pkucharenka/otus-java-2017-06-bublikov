package ru.otus.bvd.example;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.otus.bvd.example.sorter.Sorter;

public class Main {
    private static final Logger log = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) throws InterruptedException {
        configureLog();
        
        Sorter sorter = new Sorter(4);    
        int[] data = makeData();        
        //int[] data = new int[] {4,0,5,5,Integer.MAX_VALUE,9,5,8};
        
        int unsortedDataLength = data.length;
        
        sorter.setUnsortedData(data);
        sorter.schedule();
        sorter.join();
        data = sorter.getSortedData();

        if (data.length<100) {
            for (int i = 0; i < data.length; i++) {
                if (log.isLoggable(Level.INFO)) log.info( Integer.toString(i) + " = " + data[i] );             
            }            
        } else {
            for (int i = 0; i < 50; i++) {
                if (log.isLoggable(Level.INFO)) log.info( Integer.toString(i) + " = " + data[i] );             
            }
            log.info("...");
            for (int i = data.length-51; i < data.length; i++) {
                if (log.isLoggable(Level.INFO)) log.info( Integer.toString(i) + " = " + data[i]  );             
            }            
        }
        
        if (log.isLoggable(Level.INFO)) log.info("Summary: sorted data length=" + data.length + 
                ", unsorted data length=" + unsortedDataLength);
    }
    
    private static void configureLog() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        log.setLevel(Level.INFO);
        
    }

    private static int[] makeData() {
        int[] data = new int[256 * 1024 * 256];
        Random rand = new Random();
        for (int i = 0; i < data.length; i++) {
            data[i] = rand.nextInt();
        }
        
        return data;
    }
}

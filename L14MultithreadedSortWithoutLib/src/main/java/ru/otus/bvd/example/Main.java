package ru.otus.bvd.example;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import ru.otus.bvd.example.sorter.Sorter;

public class Main {
    public static final Logger log = Logger.getLogger(Main.class.getName());
    
    public static void main(String[] args) throws InterruptedException {
        configureLog();
        
        Sorter sorter = new Sorter( 4 );    
        int[] data = makeData();        
        
        sorter.setUnsortedData(data);
        sorter.schedule();
        sorter.join();
        data = sorter.getSortedData();

        for (int i = 0; i < 50; i++) {
            log.info( Integer.toString(data[i]) );             
        }
        log.info("...");
        for (int i = data.length-51; i < data.length; i++) {
            log.info( Integer.toString(data[i]) );             
        }
    }
    
    private static void configureLog() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        log.setLevel(Level.ALL);
        
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

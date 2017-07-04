package ru.otus.bvd.gcstat;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

import static ru.otus.bvd.gcstat.Main.GCGeneration.OLD;
import static ru.otus.bvd.gcstat.Main.GCGeneration.YOUNG;

//Написать приложение, которое следит за сборками мусора и пишет в лог количество сборок каждого типа
//        (young, old) и время которое ушло на сборки в минуту.


/**
 * Created by vadim on 02.07.17.
 */
public class Main {

    static long durationYongGC;
    static int countYongGC;
    static long durationOldGC;
    static int countOldGC;
    static String oldGenGC;
    static String youngGenGC;
    static int arraySize;

    enum GCGeneration {OLD, YOUNG};

    static List<Object> liquiList = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        installGCMonitoring();
        System.out.println("OLD GEN GC   : " + oldGenGC);
        System.out.println("YOUNG GEN GC : " + youngGenGC);

        int size = 10 * 1000 * 100;
        Object[] array = new Object[size];
        System.out.println("Array of size: " + array.length + " created");

        Thread thread = new Thread( new PrintStat() );
        thread.start();

        reFillArray(array);
        thread.interrupt();
    }

    static class PrintStat implements Runnable {
        boolean interrupted;
        int count;
        @Override
        public void run() {
            while (!interrupted) {
                StringBuilder sb1 = new StringBuilder();
                System.out.println(count + " yong " + youngGenGC + " duration=" + durationYongGC + " count=" + countYongGC);
                System.out.println(count + " old " + oldGenGC + " duration=" + durationOldGC + " count=" + countOldGC);
                count++;

                synchronized (Main.class) {
                    durationYongGC = 0;
                    durationOldGC = 0;
                    countYongGC = 0;
                    countOldGC = 0;
                }

                try {
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException e) {
                    interrupted = true;
                }
                if (Thread.currentThread().isInterrupted())
                    interrupted = true;
            }
        }
    }


    private static void reFillArray(Object[] array) throws InterruptedException {
        int size = array.length;
        int n = 0;
        System.out.println("Starting the loop");
        Long startTime = System.currentTimeMillis();
        while ((System.currentTimeMillis()-startTime)<1000*370 &&  n < Integer.MAX_VALUE) {
            int i = n % size;
            Object object = new String(new char[0]);  //no String pool
            array[i] = object;
            if (n % 50 == 0)
                liquiList.add(object);
            n++;
            if (n % size == 0) {
                if (n % (size*100)==0 ) {
                    logs(n);
                   // Thread.sleep(1000);
                }
                array = new Object[size];
            }
        }
    }


    private static void logs(int n) {
        System.out.println("Created " + n + " objects");
        System.out.println("Creating new array");
    }

    private static void installGCMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            if (gcGenerationResolve(gcbean.getName())==OLD) {
                oldGenGC = gcbean.getName();
            } else {
                youngGenGC = gcbean.getName();
            }
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());

                    long duration = info.getGcInfo().getDuration();
                    String gctype = info.getGcName();
                    synchronized (Main.class) {
                        if (gcGenerationResolve(gctype) == YOUNG) {
                            durationYongGC = durationYongGC + duration;
                            countYongGC++;
                        } else {
                            durationOldGC = durationOldGC + duration;
                            countOldGC++;
                        }
                    }
                }
            };

            emitter.addNotificationListener(listener, null, null);
        }
    }

    private static GCGeneration gcGenerationResolve (String gcname) {
        switch ( gcname ) {
            case "Copy":
                return YOUNG;
            case "PS Scavenge":
                return YOUNG;
            case "ParNew":
                return YOUNG;
            case "G1 Young Generation":
                return YOUNG;
            case "MarkSweepCompact":
                return OLD;
            case "PS MarkSweep":
                return OLD;
            case "ConcurrentMarkSweep":
                return OLD;
            case "G1 Old Generation":
                return OLD;
            default:
                throw new RuntimeException("Not resolved GC = " + gcname);
        }
    }



}

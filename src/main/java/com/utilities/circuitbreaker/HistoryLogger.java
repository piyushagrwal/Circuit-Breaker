package com.utilities.circuitbreaker;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicLong;

public class HistoryLogger {
    private static final long PRINT_PERIOD_MILI = 180000L;
    private static final long[] TIME_BUCKETS = {2L,4L,6L,8L,10L,25L,50L,100L,250L,500L,1000L,2500L,5000L,10000L,20000L,30000L,40000L,50000L,60000L,60001L};
    public String name;
    private AtomicLong histPrintTime = new AtomicLong(System.currentTimeMillis() + PRINT_PERIOD_MILI);
    private AtomicIntegerArray histCounts = new AtomicIntegerArray(TIME_BUCKETS.length);

    public HistoryLogger(String name){
        this.name = name;
    }

    public void logTimeTaken(long time){
        int arrLen = TIME_BUCKETS.length;
        int index = 0;
        while ((TIME_BUCKETS[index] < time) && (index < arrLen)){
            index++;
        }
//        if(index >= arrLen){
//            index = arrLen - 1;
//        }
        this.histCounts.incrementAndGet(index);
        long currentPrintTime = this.histPrintTime.get();
        if(System.currentTimeMillis() >= currentPrintTime){
            long newPrintTime = System.currentTimeMillis() + PRINT_PERIOD_MILI;

        }
    }
}

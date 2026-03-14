package com.utilities.circuitbreaker;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public class SlidingWindowStats {
    private final int windowSize;
    private AtomicLong counter = new AtomicLong(0L);
    private AtomicLongArray circBuffer = null;
    private AtomicLong slidingSum = new AtomicLong(0L);

    public SlidingWindowStats(int windowSize){
        this.windowSize = windowSize;
        this.circBuffer = new AtomicLongArray(windowSize);
    }

    public void addValue(long value){
        long counterVal = this.counter.incrementAndGet();
        int arrIdx = (int) (counterVal % this.windowSize);
        long oldValue = this.circBuffer.getAndSet(arrIdx, value);

        this.slidingSum.addAndGet(value);
        this.slidingSum.addAndGet(-1L*oldValue);
    }

    public int size(){
        long counterVal = this.counter.get();
        return counterVal > this.windowSize ? this.windowSize : (int) counterVal;
    }

    public long getSlidingSum(){
        return this.slidingSum.get();
    }

    public double getSlidingAvg(){
        return size() == 0 ? 0d : ((double) getSlidingSum() / size());
    }
}

package com.utilities.lrucache;

public interface LRUCacheBean {
    int getCapacity();
    void setCapacity(int capacity);
    int getSize();
    long getHitCount();
    long getMissCount();
    long getPutCount();
    long getDelCount();
    long getEviCount();
    double getHitRatio();
    void clear();

}

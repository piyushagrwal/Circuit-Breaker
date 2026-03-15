package com.utilities.lrucache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K,V> implements LRUCacheBean {

    private LRUCache<K,V>.CacheHolder cache = null;
    private long hitCount = 0L;
    private long misCount = 0L;
    private long putCount = 0L;
    private long delCount = 0L;
    private long eviCount = 0L;

    public LRUCache(int cacheCapacity){
        this.cache = new CacheHolder(cacheCapacity);
    }

    public V get(K key){
        Object value;
        synchronized (this.cache){
            value = this.cache.get(key);
            if(value != null){
                this.hitCount += 1L;
            }else{
                this.misCount += 1L;
                return null;
            }
        }
        return (V) value;
    }

    public void put(K key, V value){
        synchronized (this.cache){
            this.cache.put(key,value);
            this.putCount += 1L;
        }
    }

    public boolean containsKey(K key){
        synchronized (this.cache){
            return this.cache.containsKey(key);
        }
    }

    public void remove(K key){
        synchronized (this.cache){
            this.cache.remove(key);
            this.delCount += 1L;
        }
    }
    @Override
    public void clear(){
        synchronized (this.cache){
            this.cache.clear();
            this.hitCount = 0L;
            this.misCount = 0L;
            this.putCount = 0L;
            this.delCount = 0L;
            this.eviCount = 0L;
        }
    }

    @Override
    public int getCapacity() {
        return this.cache.cacheCapacity;
    }

    @Override
    public void setCapacity(int capacity) {
        if(capacity >= 0){
            synchronized (this.cache){
                this.cache.cacheCapacity = capacity;
            }
        }
    }

    @Override
    public int getSize() {
        return this.cache.size();
    }

    @Override
    public long getHitCount() {
        return this.hitCount;
    }

    @Override
    public long getMissCount() {
        return this.misCount;
    }

    @Override
    public long getPutCount() {
        return this.putCount;
    }

    @Override
    public long getDelCount() {
        return this.delCount;
    }

    @Override
    public long getEviCount() {
        return this.eviCount;
    }

    @Override
    public double getHitRatio() {
        if(this.hitCount + this.misCount == 0L){
            return 0.0D;
        }
        return ((double) this.hitCount / (this.hitCount + this.misCount));
    }


    private class CacheHolder extends LinkedHashMap<K,V>{
        private int cacheCapacity;
        public CacheHolder(int cacheCapacity){
            super(cacheCapacity, 0.75f, true);
            this.cacheCapacity = cacheCapacity;
        }
        protected boolean removeEldestEntry(Map.Entry<K,V> eldest){
            if(size() > this.cacheCapacity){
                LRUCache.this.eviCount += 1;
                return true;
            }
            return false;
        }
    }
}

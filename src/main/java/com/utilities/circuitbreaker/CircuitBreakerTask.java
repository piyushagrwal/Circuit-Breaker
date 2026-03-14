package com.utilities.circuitbreaker;

import java.util.concurrent.CountDownLatch;

public abstract class CircuitBreakerTask implements Runnable{
    private String description = null;
    private Thread workerThread = null;
    private CountDownLatch latch = new CountDownLatch(1);
    private Object returnValue;
    private Exception exception = null;

    public CircuitBreakerTask(String description){
        this.description = description;
    }
    final Thread getWorkerThread(){
        return this.workerThread;
    }
    final CountDownLatch getLatch(){
        return this.latch;
    }
    protected final void setReturnValue(Object returnValue){
        this.returnValue = returnValue;
    }
    public final Object getReturnValue(){
        return this.returnValue;
    }
    public final boolean hasException(){
        return this.exception != null;
    }
    public final Exception getException(){
        return this.exception;
    }
    public final String getDescription(){
        return this.description;
    }

    public final void run(){
        this.workerThread = Thread.currentThread();
        try {
            cbOperation();
        }catch (Exception e){
            this.exception = e;
        }finally {
            this.workerThread = null;
            this.latch.countDown();
        }
    }

    protected abstract void cbOperation() throws Exception;

    protected void onTimeOut(){}
}

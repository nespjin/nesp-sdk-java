package com.nesp.sdk.java.log;

public interface TaskLoopThread extends Runnable {

    void addTask(Runnable task) throws InterruptedException;

    void start();

    void interrupt();

    boolean isAlive();
}

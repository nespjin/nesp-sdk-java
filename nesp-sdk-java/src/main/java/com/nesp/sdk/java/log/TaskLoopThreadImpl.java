package com.aucma.lang.log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

class TaskLoopThreadImpl extends Thread implements TaskLoopThread {

    private final BlockingQueue<Runnable> tasks = new ArrayBlockingQueue<>(2000);

    {
        setName("TaskLoopThread");
    }

    @Override
    public void run() {
        super.run();
        try {
            while (!isInterrupted()) {
                Runnable runnable;
                while ((runnable = tasks.take()) != null) {
                    try {
                        runnable.run();
                    } catch (Exception ignored) {
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void addTask(Runnable task) throws InterruptedException {
        this.tasks.put(task);
    }

    @Override
    public void interrupt() {
        super.interrupt();
    }
}

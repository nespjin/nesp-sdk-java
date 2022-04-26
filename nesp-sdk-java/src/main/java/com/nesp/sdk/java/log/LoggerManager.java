package com.nesp.sdk.java.log;

import java.util.WeakHashMap;

public class LoggerManager {

    private static TaskLoopThread taskLoopThread;
    private static Logger.Config config;

    private static final WeakHashMap<String/*class name*/, Logger> loggerMap = new WeakHashMap<>();

    public static synchronized void setTaskLoopThread(TaskLoopThread loop) {
        LoggerManager.taskLoopThread = loop;
    }

    public static synchronized TaskLoopThread getTaskLoopThread() {
        if (taskLoopThread == null) {
            taskLoopThread = new TaskLoopThreadImpl();
        }
        if (!taskLoopThread.isAlive()) {
            taskLoopThread.start();
        }
        return taskLoopThread;
    }

    public static synchronized void setConfig(Logger.Config config) {
        LoggerManager.config = config;
    }

    public static synchronized Logger.Config getConfig() {
        return LoggerManager.config == null ? new DefaultLogConfig() : LoggerManager.config;
    }

    public static synchronized Logger getLogger(Class<?> clazz) {
        if (loggerMap.containsKey(clazz.getName()) && loggerMap.get(clazz.getName()) != null) {
            return loggerMap.get(clazz.getName());
        }
        LoggerImpl logger = new LoggerImpl(clazz);
        loggerMap.put(clazz.getName(), logger);
        return logger;
    }

    public static synchronized Logger getLogger(String name) {
        if (loggerMap.containsKey(name) && loggerMap.get(name) != null) {
            return loggerMap.get(name);
        }
        LoggerImpl logger = new LoggerImpl(name);
        loggerMap.put(name, logger);
        return logger;
    }
}

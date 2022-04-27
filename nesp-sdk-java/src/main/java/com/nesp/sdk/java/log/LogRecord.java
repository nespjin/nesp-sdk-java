package com.nesp.sdk.java.log;

public class LogRecord {
    public long time;
    public long pid;
    public Thread thread;
    public String logName;
    public String className;
    public String methodName;
    public int lineNumber;
    public Logger.Level level;
    public String message;
    public String messageFormatted;
    public Throwable throwable;
}

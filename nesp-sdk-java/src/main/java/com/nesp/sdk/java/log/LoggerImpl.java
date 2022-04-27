package com.aucma.lang.log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * @author jinzhaolu
 */
class LoggerImpl implements Logger {

    private final String className;
    private Config config;

    public LoggerImpl(Class<?> clazz) {
        this.className = clazz.getSimpleName();
    }

    public LoggerImpl(String name) {
        this.className = name;
    }

    @Override
    public void log(Level level, String message) {
        log(level, message, null);
    }

    @Override
    public void log(Level level, String message, Throwable t) {
        if (getConfig().getLevel().compareTo(level) > 0) {
            return;
        }

        final StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        StackTraceElement caller = null;
        for (int i = 2, traceLength = trace.length; i < traceLength; i++) {
            StackTraceElement stackTraceElement = trace[i];
            if (stackTraceElement.getClassName().equals(this.getClass().getName())) {
                continue;
            }

            if (stackTraceElement.getClassName().equals(Logger.class.getName())) {
                continue;
            }

            caller = stackTraceElement;
            break;
        }

        String callerClassName = "?";
        String callerMethodName = "?";
        int callerLineNumber = -1;

        if (caller != null) {
            callerClassName = caller.getClassName();
            callerMethodName = caller.getMethodName();
            callerLineNumber = caller.getLineNumber();
        }

        final Config config = getConfig();
        if (config == null) {
            throw new NullPointerException("Logger config is null, please set it first");
        }

        final Format format = config.getFormat();
        if (format == null) {
            throw new NullPointerException("Logger format is null, please set it first");
        }

        final LogRecord record = new LogRecord();
        record.time = System.currentTimeMillis();

        Class<?> aProcessCls = null;
        try {
            aProcessCls = Class.forName("android.os.Process");
        } catch (ClassNotFoundException ignored) {
        }

        long myPid = -1;
        if (aProcessCls != null) {
            try {
                myPid = (int) aProcessCls.getMethod("myPid").invoke(null);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
            }
        }

        if (myPid == -1) {
            try {
                final Class<?> processHandleCls = Class.forName("java.lang.ProcessHandle");
                final Method mcurrent = processHandleCls.getDeclaredMethod("current");
                mcurrent.setAccessible(true);
                final Method mpid = processHandleCls.getDeclaredMethod("pid");
                mpid.setAccessible(true);
                final Object curProcessHandle = mcurrent.invoke(null);
                final Long invokeRet = (Long) mpid.invoke(curProcessHandle);
                if (invokeRet != null) {
                    myPid = (long) invokeRet;
                }
            } catch (ClassNotFoundException | IllegalAccessException
                    | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        record.pid = myPid;
        record.logName = className;
        record.className = callerClassName;
        record.methodName = callerMethodName;
        record.lineNumber = callerLineNumber;
        record.level = level;
        record.message = message;
        record.thread = Thread.currentThread();
        record.throwable = t;

        record.messageFormatted = format.format(record);

        if (getConfig().getFilter() != null && !getConfig().getFilter().isLoggable(record)) {
            return;
        }

        final Set<Printer> printers = config.getPrinters();
        if (printers == null || printers.isEmpty()) {
            throw new NullPointerException("Logger printers is null, please set it first");
        }

        for (Printer printer : printers) {
            try {
                printer.print(record);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public Config getConfig() {
        return config == null ? LoggerManager.getConfig() : config;
    }
}

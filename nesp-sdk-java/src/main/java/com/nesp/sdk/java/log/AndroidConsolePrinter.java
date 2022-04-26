package com.nesp.sdk.java.log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AndroidConsolePrinter implements Logger.Printer {

    private static final String TAG = "AndroidConsolePrinter";
    private final Method aLogi;
    private final Method aLogd;
    private final Method aLogw;
    private final Method aLoge;

    {
        try {
            Class<?> aLogCls = Class.forName("android.util.Log");
            aLogi = aLogCls.getMethod("i", String.class, String.class, Throwable.class);
            aLogd = aLogCls.getMethod("d", String.class, String.class, Throwable.class);
            aLogw = aLogCls.getMethod("w", String.class, String.class, Throwable.class);
            aLoge = aLogCls.getMethod("e", String.class, String.class, Throwable.class);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void print(LogRecord record) throws IOException {
        try {
            switch (record.level) {
                case INFO:
                    aLogi.invoke(null, record.logName, record.message, record.throwable);
                    break;
                case DEBUG:
                    aLogd.invoke(null, record.logName, record.message, record.throwable);
                    break;
                case WARN:
                    aLogw.invoke(null, record.logName, record.message, record.throwable);
                    break;
                case ERROR:
                case FATAL:
                    aLoge.invoke(null, record.logName, record.message, record.throwable);
                    break;
                default:
                    System.out.println(record.message);
                    break;
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}

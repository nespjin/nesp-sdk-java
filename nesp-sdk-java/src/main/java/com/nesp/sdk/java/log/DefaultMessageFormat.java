package com.nesp.sdk.java.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DefaultMessageFormat implements Logger.Format {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public String format(LogRecord record) {
        String t = "";
        if (record.throwable != null) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            pw.println();
            record.throwable.printStackTrace(pw);
            pw.close();
            t = sw.toString();
        }

        final String threadName = record.thread.getName();
        return String.format(Locale.CHINA,
                "%s\t%s\t%d\t[%s]\t%s\t%s.%s %d:\t%s",
                dateFormat.format(record.time),
                record.level.name(),
                record.pid,
                threadName,
                record.logName,
                record.className,
                record.methodName,
                record.lineNumber,
                record.message) + t;
    }
}

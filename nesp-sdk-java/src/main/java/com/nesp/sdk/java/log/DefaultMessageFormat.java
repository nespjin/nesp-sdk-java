package com.nesp.sdk.java.log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DefaultMessageFormat implements Logger.Format {

//    2022-04-25 13:57:29.118  INFO 13092 --- [nio-8081-exec-1] o.s.web.servlet.DispatcherServlet        : Initializing Servlet 'dispatcherServlet'

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
//        return dateFormat.format(System.currentTimeMillis()) + " " + record.level.name() + " " + record.pid +
//                " --- " + "[" + record.thread.getName() + "] " + record.className + "." + record.methodName + " "
//                + record.lineNumber + " :" + " " + record.message;
    }
}

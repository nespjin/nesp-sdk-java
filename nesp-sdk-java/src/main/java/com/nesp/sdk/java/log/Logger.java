package com.nesp.sdk.java.log;

import java.io.IOException;
import java.util.Set;

/**
 * The simple logger.
 *
 * @author jinzhaolu
 */
public interface Logger {

    static Logger getLogger(Class<?> clazz) {
        return LoggerManager.getLogger(clazz);
    }

    static Logger getLogger(String name) {
        return LoggerManager.getLogger(name);
    }

    enum Level {
        DEBUG, INFO, WARN, ERROR, FATAL
    }

    void log(Level level, String message);

    void log(Level level, String message, Throwable t);

    default void debug(String message) {
        log(Level.DEBUG, message);
    }

    default void debug(String message, Throwable t) {
        log(Level.DEBUG, message, t);
    }

    default void info(String message) {
        log(Level.INFO, message);
    }

    default void info(String message, Throwable t) {
        log(Level.INFO, message, t);
    }

    default void warn(String message) {
        log(Level.WARN, message);
    }

    default void warn(String message, Throwable t) {
        log(Level.WARN, message, t);
    }

    default void error(String message) {
        log(Level.ERROR, message);
    }

    default void error(String message, Throwable t) {
        log(Level.ERROR, message, t);
    }

    default void fatal(String message) {
        log(Level.FATAL, message);
    }

    default void fatal(String message, Throwable t) {
        log(Level.FATAL, message, t);
    }

    void setConfig(Config config);

    Config getConfig();

    interface Printer {
        void print(LogRecord record) throws IOException;
    }

    interface Format {

        /**
         * Format the log message with the given parameters.
         *
         * @param record@return the formatted log message
         */
        String format(LogRecord record);
    }

    interface Config {

        void setFormat(Format format);

        Format getFormat();

        void setLevel(Level level);

        Level getLevel();

        void addPrinter(Printer printer);

        void removePrinter(Printer printer);

        Set<Printer> getPrinters();
    }
}

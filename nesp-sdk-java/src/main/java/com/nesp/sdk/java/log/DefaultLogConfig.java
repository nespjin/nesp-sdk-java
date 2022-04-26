package com.nesp.sdk.java.log;

import java.util.LinkedHashSet;
import java.util.Set;

class DefaultLogConfig implements Logger.Config {

    private Logger.Format format;
    private Logger.Level level;
    private final Set<Logger.Printer> printers = new LinkedHashSet<>();

    {
        if (isAndroid()) {
            printers.add(new AndroidConsolePrinter());
        } else {
            printers.add(new ConsolePrinter());
        }
    }

    private boolean isAndroid() {
        try {
            Class.forName("android.util.Log");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    @Override
    public void setFormat(Logger.Format format) {
        this.format = format;
    }

    @Override
    public Logger.Format getFormat() {
        return format == null ? new DefaultMessageFormat() : format;
    }

    @Override
    public void setLevel(Logger.Level level) {
        this.level = level;
    }

    @Override
    public Logger.Level getLevel() {
        return level == null ? Logger.Level.INFO : level;
    }

    @Override
    public void addPrinter(Logger.Printer printer) {
        printers.add(printer);
    }

    @Override
    public void removePrinter(Logger.Printer printer) {
        printers.remove(printer);
    }

    @Override
    public Set<Logger.Printer> getPrinters() {
        return printers;
    }
}

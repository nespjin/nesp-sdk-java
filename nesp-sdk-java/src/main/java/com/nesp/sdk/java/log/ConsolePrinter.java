package com.aucma.lang.log;

import java.io.IOException;

public class ConsolePrinter implements Logger.Printer {

    @Override
    public void print(LogRecord record) throws IOException {
        switch (record.level) {
            case WARN:
                System.out.println(getFormatLogString(record.messageFormatted, 32, 0));
                break;
            case ERROR:
                System.out.println(getFormatLogString(record.messageFormatted, 31, 0));
                break;
            case FATAL:
                System.out.println(getFormatLogString(record.messageFormatted, 31, 1));
                break;
            default:
                System.out.println(record.messageFormatted);
                break;
        }
    }

    /**
     * @param colour  颜色代号：背景颜色代号(41-46)；前景色代号(31-36)
     * @param type    样式代号：0无；1加粗；3斜体；4下划线
     * @param content 要打印的内容
     */
    private static String getFormatLogString(String content, int colour, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", colour, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", colour, type, content);
        }
    }
}

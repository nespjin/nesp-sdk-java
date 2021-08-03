package com.nesp.sdk.java.text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/7/27 15:17
 **/
public final class DateUtils {

    public static final String DATE_FORMAT_PATTERN_FULL_TYPE_ONE = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_PATTERN_DATE_TYPE_ONE = "yyyy-MM-dd";

    public static final SimpleDateFormat DATE_FORMAT_FULL_TYPE_ONE =
            newSimpleDateFormat(DATE_FORMAT_PATTERN_FULL_TYPE_ONE);

    public static final SimpleDateFormat DATE_FORMAT_DATE_TYPE_ONE =
            newSimpleDateFormat(DATE_FORMAT_PATTERN_DATE_TYPE_ONE);

    public static String tryFormatDate(SimpleDateFormat simpleDateFormat, String dateSource) {
        try {
            return formatDate(simpleDateFormat, DateFormat.getInstance().parse(dateSource));
        } catch (ParseException e) {
            return dateSource;
        }
    }

    public static String formatDate(SimpleDateFormat simpleDateFormat, String date) throws ParseException {
        return formatDate(simpleDateFormat, DateFormat.getInstance().parse(date));
    }

    public static String formatDate(SimpleDateFormat simpleDateFormat, Date date) {
        return simpleDateFormat.format(date);
    }

    public static SimpleDateFormat newSimpleDateFormat(String pattern) {
        return new SimpleDateFormat(pattern, Locale.CHINA);
    }
}

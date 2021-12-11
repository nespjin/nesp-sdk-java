package com.nesp.sdk.java.text;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/2 16:51
 **/
@SuppressWarnings("unused")
public final class TextUtil {
    private static final String TAG = "TextUtil";

    private TextUtil() { /* cannot be instantiated */ }

    public static CharSequence ifEmpty(CharSequence cs, CharSequence csIfEmpty) {
        if (isEmpty(cs)) {
            return csIfEmpty;
        }
        return cs;
    }

    public static boolean exitsEmpty(CharSequence... charSequences) {
        if (charSequences == null || charSequences.length == 0) {
            return true;
        }

        for (final CharSequence charSequence : charSequences) {
            if (isEmpty(charSequence)) return true;
        }

        return false;
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * @param text 要判断的内容
     * @return 是否为数字
     */
    public static boolean isNumber(final String text) {
        if (isEmpty(text)) return false;
        // String reg = "^[0-9]+(.[0-9]+)?$";
        final String reg = "^(-?\\d+)(\\.\\d+)?";
        return text.matches(reg);
    }

    /**
     * @param text 要判断的内容
     * @return 是否为数字或大小字母
     */
    public static boolean isNumberOrLetter(final String text) {
        if (isEmpty(text)) return false;
        final String reg = "^[a-z0-9A-Z]+$";
        return text.matches(reg);
    }

    /**
     * @param text 要判断的内容
     * @return 是否为Email
     */
    public static boolean isEmail(String text) {
        if (isEmpty(text)) return false;
        return text.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
    }

    /**
     * @param text 要判断的内容
     * @return 是否为中文
     */
    public static boolean isChinese(String text) {
        if (isEmpty(text)) return false;
        return text.matches("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$");
    }

    public static boolean isHex(String key) {
        for (int i = key.length() - 1; i >= 0; i--) {
            final char c = key.charAt(i);
            if (!(c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f')) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param text   要判断的内容
     * @param minLen 最小长度 必须 > 0
     * @param maxLen 最大长度 必须 > minLen
     * @return 是否为中文或数字或字母并且长度在限制范围内
     */
    public static boolean isChineseOrNumberOrLetterLimitIn(final String text,
                                                           final int minLen,
                                                           final int maxLen) {
        if (isEmpty(text)) return false;
        if (minLen < 0) throw new IllegalArgumentException("minLen must > 0");
        if (maxLen < minLen) throw new IllegalArgumentException("maxLen must > minLen");
        return text.matches("^[\\u4E00-\\u9FA5\\uF900-\\uFA2D\\w]{" + minLen + "," + maxLen + "}$");
    }

    public static boolean isInteger(final String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Integer toInteger(final String text, final Integer defaultValue) {
        return toInteger(text, 10, defaultValue);
    }

    public static Integer toInteger(final String text, final int radix, final Integer defaultValue) {
        try {
            return Integer.parseInt(text, radix);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean isFloat(final String text) {
        try {
            Float.parseFloat(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Float toFloat(final String text, final Float defaultValue) {
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * @param text text
     * @return 是否为Double值
     */
    public static boolean isDouble(final String text) {
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Double toDouble(final String text, final Double defaultValue) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }


    /**
     * @param text text
     * @return 是否为链接
     */
    public static boolean isUrl(final String text) {
        return text.startsWith("https://") || text.startsWith("http://");
    }
}

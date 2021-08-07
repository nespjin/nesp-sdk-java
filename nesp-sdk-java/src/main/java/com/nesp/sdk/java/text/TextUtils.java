package com.nesp.sdk.java.text;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/8/2 16:51
 **/
public class TextUtils {
    private static final String TAG = "TextUtils";

    private TextUtils() { /* cannot be instantiated */ }

    public static CharSequence ifEmpty(CharSequence cs, CharSequence csIfEmpty) {
        if (isEmpty(cs)) {
            return csIfEmpty;
        }
        return cs;
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

}

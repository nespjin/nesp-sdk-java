/*
 * Copyright (c) 2022.   NESP Technology.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.nesp.sdk.java.util;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/9/3 16:24
 **/
public final class StringUtil {
    private static final String TAG = "StringUtil";

    private StringUtil() {
    }

    public static String join(String separator, String... strings) {
        return join(separator, ListUtil.of(strings));
    }

    public static String join(String separator, Collection<String> strings) {
        return join(separator, strings.iterator());
    }

    public static String join(String separator, Iterator<String> stringIterator) {
        StringBuilder rStringBuilder = new StringBuilder();
        while (stringIterator.hasNext()) {
            if (rStringBuilder.length() > 0) {
                rStringBuilder.append(",");
            }
            rStringBuilder.append(stringIterator.next());
        }
        return rStringBuilder.toString();
    }
}

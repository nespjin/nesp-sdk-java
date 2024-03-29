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

package com.nesp.sdk.java.ranges;

/**
 * Team: NESP Technology
 *
 * @author <a href="mailto:1756404649@qq.com">Jinzhaolu Email:1756404649@qq.com</a>
 * Time: Created 2020/9/27 3:12
 * Project: nesp-sdk-java
 **/
public class RangeUtil {

    /**
     * Ensures that this value is not less than the specified minimumValue.
     *
     * @param value        origin value.
     * @param minimumValue the specified minimumValue.
     * @return this value if it's greater than or equal to the minimumValue or the minimumValue otherwise.
     */
    public static int coerceAtLeast(int value, int minimumValue) {
        return Math.max(value, minimumValue);
    }
}

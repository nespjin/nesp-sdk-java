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

package com.nesp.sdk.java.cache;

import java.util.HashSet;
import java.util.Set;

/**
 * Team: NESP Technology
 *
 * @author <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * @version 1.0
 * Time: Created 4/10/2022 12:07 AM
 * Description:
 **/
public abstract class Cache2 implements Cache {

    @Override
    public Boolean getBoolean(final String key) {
        return getBoolean(key, false);
    }

    public abstract Boolean getBoolean(String key, boolean defValue);

    @Override
    public Byte getByte(final String key) {
        return getByte(key, (byte) 0);
    }

    public abstract Byte getByte(String key, byte defValue);

    @Override
    public Short getShort(final String key) {
        return getShort(key, (short) 0);
    }

    public abstract Short getShort(String key, short defValue);

    @Override
    public Integer getInt(final String key) {
        return getInt(key, 0);
    }

    public abstract Integer getInt(String key, int defValue);

    @Override
    public Long getLong(final String key) {
        return getLong(key, 0L);
    }

    public abstract Long getLong(String key, long defValue);

    @Override
    public Float getFloat(final String key) {
        return getFloat(key, 0f);
    }

    public abstract Float getFloat(String key, float defValue);

    @Override
    public Double getDouble(final String key) {
        return getDouble(key, 0d);
    }

    public abstract Double getDouble(String key, double defValue);

    @Override
    public String getString(final String key) {
        return getString(key, "");
    }

    public abstract String getString(String key, String defValue);

    @Override
    public Set<String> getStringSet(final String key) {
        return getStringSet(key, new HashSet<>());
    }

    public abstract Set<String> getStringSet(String key, Set<String> defValue);
}

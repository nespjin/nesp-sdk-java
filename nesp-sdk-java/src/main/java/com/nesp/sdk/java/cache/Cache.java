package com.nesp.sdk.java.cache;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Team: NESP Technology
 * Author: <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 2021/11/13 下午12:31
 * Description:
 **/
public interface Cache {

    void putBoolean(String key, boolean value);

    void putByte(String key, byte value);

    void putShort(String key, short value);

    void putInt(String key, int value);

    void putLong(String key, long value);

    void putFloat(String key, float value);

    void putDouble(String key, double value);

    void putString(String key, String value);

    void putStringSet(String key, Set<String> value);

    Boolean getBoolean(String key, boolean defValue);

    Byte getByte(String key, byte defValue);

    Short getShort(String key, short defValue);

    Integer getInt(String key, int defValue);

    Long getLong(String key, long defValue);

    Float getFloat(String key, float defValue);

    Double getDouble(String key, double defValue);

    String getString(String key, String defValue);

    Set<String> getStringSet(String key, Set<String> defValue);

    Map<String, Object> getAll();

    boolean remove(String key);

    boolean contains(String key);

    Set<String> keys();

    List<Object> values();
}

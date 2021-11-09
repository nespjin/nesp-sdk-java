package com.nesp.sdk.java.util;

import com.nesp.sdk.java.annotation.NonNull;
import com.nesp.sdk.java.annotation.Nullable;

import java.util.Objects;

/**
 * @author <a href="mailto:1756404649@qq.com">靳兆鲁 Email:1756404649@qq.com</a>
 * Time: Created 2021/7/27 12:37
 **/
public final class NullableObject<T> {

    public static final NullableObject<?> EMPTY = new NullableObject<>();

    @Nullable
    private final T value;

    public static <T> NullableObject<T> of(@NonNull T value) {
        Objects.requireNonNull(value);
        return new NullableObject<>(value);
    }

    public static <T> NullableObject<T> ofNullable(@Nullable T value) {
        return new NullableObject<>(value);
    }

    public <R> NullableObject<R> map(@NonNull Mapper<R, T> mapper) {
        if (!isPresent()) {
            return empty();
        } else {
            return ofNullable(mapper.map(value));
        }
    }

    public T orElse(T other) {
        return value == null ? other : value;
    }

    public String orString(String other) {
        return value == null ? other : String.valueOf(value);
    }

    public String orEmptyString() {
        return value == null ? "" : String.valueOf(value);
    }

    public static <T> NullableObject<T> empty() {
        @SuppressWarnings("unchecked")
        NullableObject<T> t = (NullableObject<T>) EMPTY;
        return t;
    }


    public boolean isPresent() {
        return value != null;
    }

    private NullableObject() {
        this(null);
    }

    private NullableObject(@Nullable T value) {
        this.value = value;
    }


}

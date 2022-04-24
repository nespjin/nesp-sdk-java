/*
 * Copyright (c) 2021-2022.   NESP Technology.
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

package com.nesp.sdk.java.lang;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.Callable;

/**
 * Team: NESP Technology
 *
 * @author <a href="mailto:1756404649@qq.com">JinZhaolu Email:1756404649@qq.com</a>
 * Time: Created 4/25/2022 12:04 AM
 * Description:
 **/
public class Result<T> implements Serializable {
    @Nullable
    final Object value;

    public Result(@Nullable Object value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return !(value instanceof Failure);
    }

    public boolean isFailure() {
        return value instanceof Failure;
    }

    @Nullable
    @SuppressWarnings("unchecked")
    public T getOrNull() {
        if (isFailure()) {
            return null;
        }
        return (T) value;
    }

    @Nullable
    public Throwable exceptionOrNull() {
        if (value instanceof Failure) {
            return ((Failure) value).exception;
        }
        return null;
    }

    @Override
    public String toString() {
        if (value instanceof Failure) {
            return ((Failure) value).toString();
        }
        return "Success(" + value + ")";
    }

    public static <T> Result<T> success(T value) {
        Objects.requireNonNull(value, "value is null");
        return new Result<>(value);
    }

    public static <T> Result<T> failure(Throwable exception) {
        return new Result<>(createFailure(exception));
    }

    static class Failure implements Serializable {
        private final Throwable exception;

        public Failure(Throwable exception) {
            Objects.requireNonNull(exception, "exception is null");
            this.exception = exception;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Failure && exception.equals(((Failure) obj).exception);
        }

        @Override
        public int hashCode() {
            return exception.hashCode();
        }

        @Override
        public String toString() {
            return "Failure(" + exception + ")";
        }
    }

    static Object createFailure(Throwable exception) {
        return new Failure(exception);
    }

    void throwOnFailure() throws Throwable {
        if (value instanceof Failure) throw ((Failure) value).exception;
    }

    public static <R> Result<R> runCatching(Callable<R> block) {
        try {
            return Result.success(block.call());
        } catch (Throwable e) {
            return Result.failure(e);
        }
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public T getOrThrow() throws Throwable {
        throwOnFailure();
        Objects.requireNonNull(value, "value is null");
        return (T) value;
    }

    @SuppressWarnings("unchecked")
    public T getOrElse(ThrowableHandle<T> handle) {
        final Throwable ex = exceptionOrNull();
        if (ex == null) return (T) value;
        return handle.handle(ex);
    }

    @SuppressWarnings("unchecked")
    public T getOrDefault(T defaultValue) {
        if (isFailure()) return defaultValue;
        return (T) value;
    }

    public interface ThrowableHandle<T> {
        T handle(Throwable throwable);
    }
}

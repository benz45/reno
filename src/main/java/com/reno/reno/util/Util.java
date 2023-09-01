package com.reno.reno.util;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class Util {
    private Util() {
    }

    public static <T> boolean isNotNull(T value) {
        return value != null;
    }

    public static <V> void setIfNotNull(V value, Consumer<V> setter) {
        if (Objects.nonNull(value)) {
            setter.accept(value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean isNullOrEmpty(T value) {
        return value == null || ((boolean) (value instanceof String && ((String) value).isEmpty()))
                || (value instanceof List && ((boolean) ((List<T>) value).isEmpty()));
    }

}

package com.reno.reno.util;

import java.util.List;

public class Util {
    private Util() {
    }

    public static <T> boolean isNotNull(T value) {
        return value != null;
    }

    @SuppressWarnings("unchecked")
    public static <T> boolean isNullOrEmpty(T value) {
        return value == null || ((boolean) (value instanceof String && ((String) value).isEmpty()))
                || (value instanceof List && ((boolean) ((List<T>) value).isEmpty()));
    }

}

package com.reno.reno.util;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import org.modelmapper.ModelMapper;

import com.reno.reno.model.exception.ApiException;

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

    public static Integer convertStringToInteger(String value) throws ApiException {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException ex) {
            throw new ApiException("400", "Invalid " + ex.getMessage());
        }
    }

    public static <V, T> T map(V value, Class<T> type) {
        ModelMapper modelMapper = new ModelMapper();
        return (T) modelMapper.map(value, type);
    }

}

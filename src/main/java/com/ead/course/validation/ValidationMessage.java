package com.ead.course.validation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

public final class ValidationMessage {

    private ValidationMessage() {
    }

    public static String getInvalid(Class<?> fieldType, Object rejectedValue) {
        if (fieldType == null) {
            return generic(rejectedValue);
        }

        if (fieldType.isEnum()) {
            return enumMessage(fieldType, rejectedValue);
        }

        if (fieldType.equals(UUID.class)) {
            return typed("UUID", rejectedValue);
        }

        if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return typed("Integer", rejectedValue);
        }

        if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return typed("Long", rejectedValue);
        }

        if (fieldType.equals(BigDecimal.class) || fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return typed("Decimal", rejectedValue);
        }

        if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {

            return typed("Boolean", rejectedValue);
        }

        return generic(rejectedValue);
    }

    private static String typed(String type, Object value) {
        return String.format("Invalid %s value '%s'", type, value);
    }

    private static String generic(Object value) {
        return String.format("Invalid value '%s'", value);
    }

    private static String enumMessage(Class<?> fieldType, Object value) {
        String acceptedValues = Arrays.stream(fieldType.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return String.format("Invalid value '%s'. Accepted values: [%s]", value, acceptedValues);
    }
}
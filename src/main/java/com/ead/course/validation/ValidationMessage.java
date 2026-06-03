package com.ead.course.validation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public final class ValidationMessage {

    private static final Map<Class<?>, String> TYPE_MAPPINGS =
            Map.ofEntries(
                    Map.entry(UUID.class, "UUID"),
                    Map.entry(Integer.class, "Integer"),
                    Map.entry(int.class, "Integer"),
                    Map.entry(Long.class, "Long"),
                    Map.entry(long.class, "Long"),
                    Map.entry(BigDecimal.class, "Decimal"),
                    Map.entry(Double.class, "Decimal"),
                    Map.entry(double.class, "Decimal"),
                    Map.entry(Float.class, "Decimal"),
                    Map.entry(float.class, "Decimal"),
                    Map.entry(Boolean.class, "Boolean"),
                    Map.entry(boolean.class, "Boolean"),
                    Map.entry(LocalDate.class, "Date (yyyy-MM-dd)"),
                    Map.entry(LocalDateTime.class, "DateTime"),
                    Map.entry(LocalTime.class, "Time")
            );

    private ValidationMessage() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String getInvalid(Class<?> fieldType, Object rejectedValue) {
        if (fieldType == null) {
            return generic(rejectedValue);
        }

        Class<?> enumClass = targetEnumClass(fieldType);
        if (enumClass != null) {
            return enumMessage(enumClass, rejectedValue);
        }

        String typeName = TYPE_MAPPINGS.get(fieldType);
        if (typeName != null) {
            return typed(typeName, rejectedValue);
        }

        return generic(rejectedValue);
    }

    private static String typed(String type, Object value) {
        return String.format("Invalid %s value '%s'", type, value);
    }

    private static String generic(Object value) {
        return String.format("Invalid value '%s'", value);
    }

    private static String enumMessage(Class<?> enumType, Object value) {
        String acceptedValues = Arrays.stream(enumType.getEnumConstants())
                .map(Object::toString)
                .collect(Collectors.joining(", "));

        return String.format("Invalid Enum value '%s'. Accepted values: [%s]", value, acceptedValues);
    }

    private static Class<?> targetEnumClass(Class<?> type) {
        if (type.isEnum()) {
            return type;
        }
        if (type.getSuperclass() != null && type.getSuperclass().isEnum()) {
            return type.getSuperclass();
        }
        return null;
    }
}
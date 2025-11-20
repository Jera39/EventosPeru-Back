package com.eventosPeru.eventos.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DEFAULT_FORMATTER) : null;
    }

    public static LocalDateTime parseDateTime(String dateTimeStr) {
        return dateTimeStr != null ? LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER) : null;
    }

    public static boolean isDateInFuture(LocalDateTime date) {
        return date != null && date.isAfter(LocalDateTime.now());
    }

    public static boolean isDateInPast(LocalDateTime date) {
        return date != null && date.isBefore(LocalDateTime.now());
    }
}

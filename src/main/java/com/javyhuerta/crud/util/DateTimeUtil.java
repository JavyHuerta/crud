package com.javyhuerta.crud.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.util.ObjectUtils;

public class DateTimeUtil {

    public static final String DAY_MONTH_YEAR = "dd/MM/yyyy";

    private DateTimeUtil() {}

    public static String localDateToString(LocalDate date, String pattern) {
        if (date == null) {
            return "";
        }

        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate stringToLocalDate(String date, String pattern) {
        if (ObjectUtils.isEmpty(date)) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(date, formatter);
    }
}

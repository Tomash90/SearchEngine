package com.example.app.date;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {

    private static final String DEFAULT_PATTERN="yyyy-MM-dd";
    private static final String US_PATTERN="MM/dd/yyyy";

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return LocalDate.parse(text, DateTimeFormatter.ofPattern(getPattern(locale)));
    }

    @Override
    public String print(LocalDate object, Locale locale) {
        return DateTimeFormatter.ofPattern(getPattern(locale)).format(object);
    }

    public static String getPattern(Locale locale){
        return isUnitedState(locale) ? US_PATTERN : DEFAULT_PATTERN;
    }

    private static boolean isUnitedState(Locale locale){
        return Locale.US.getCountry().equals(locale.getCountry());
    }
}

package com.camcecil.address.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Helper functions for handling dates
 *
 * @author Marco Jakob
 *
 * http://code.makery.ch/java/javafx-8-tutorial-part1/
 */

public class DateUtil
{

    // Date pattern that is used for conversion.
    private static final String DATE_PATTERN = "MM/dd/yyyy";

    // Date formatter
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * Returns the given date as a well formatted String. The above defined
     * {@link DateUtil#DATE_PATTERN} is used.
     *
     * @param date The date to be returned as a String
     * @return Formatted String
     */
    public static String format(LocalDate date)
    {
        if (date == null) {
            return null;
        }

        return DATE_FORMATTER.format(date);
    }

    /**
     * Converts a String in the format of the defined {@link DateUtil#DATE_PATTERN}
     * to a {@link LocalDate} object.
     *
     * Returns null if the String could not be converted.
     *
     * @param dateString The Date as a String
     * @return The Date object or null if it could not be converted
     */
    public static LocalDate parse(String dateString)
    {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    /**
     * Checks whether the String is a valid Date.
     *
     * @param dateString
     * @return True if the String is a valid Date.
     */
    public static boolean isValidDate(String dateString)
    {
        // Try to parse the String.
        return DateUtil.parse(dateString) != null;
    }

    public static String getDatePattern()
    {
        return DATE_PATTERN;
    }
}

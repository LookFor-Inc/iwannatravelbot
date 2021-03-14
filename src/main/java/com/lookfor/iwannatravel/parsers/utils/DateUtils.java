package com.lookfor.iwannatravel.parsers.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    /**
     * Convert string date to Date object
     *
     * @param str date in string format
     * @return Date object
     */
    public static Date stringToDate(String str) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = null;

        try {
            date = inputFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}

package org.eu.leto.core.util;


import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public final class DateUtils {
    private DateUtils() {
    }


    public static Date createDate(int year, int month, int day, int hour,
            int min, int sec) {
        return new GregorianCalendar(year, month, day, hour, min, sec)
                .getTime();
    }


    public static Date createDate(int year, int month, int day) {
        return createDate(year, month, day, 0, 0, 0);
    }


    public static Date trimToDays(Date date) {
        final Calendar cal = new GregorianCalendar();
        cal.setTime(date);

        return trimToDays(cal).getTime();
    }


    public static Calendar trimToDays(Calendar cal) {
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal;
    }
}

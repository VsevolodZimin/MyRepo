package utils;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DateUtil {
    public static Date getDate() {
        int day = GregorianCalendar.DAY_OF_MONTH;
        int year = GregorianCalendar.YEAR;
        int month = Calendar.JANUARY;
        GregorianCalendar calendar = new GregorianCalendar(year, month, day);
        return new Date(calendar.getTimeInMillis());
    }
}

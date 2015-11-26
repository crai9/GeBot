package com.craig.gebot.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.util.concurrent.TimeUnit;

/**
 * Created by craig on 06/11/2015.
 */
public class TimeUtils {

    public static void main(String args[]){

        System.out.println(daysSinceDate("06/11/2015 00:00:00"));
    }

    public static double daysSinceDate(String startDate) {

        //DateTime now = new DateTime(DateTimeZone.UTC);
        DateTime now = DateTime.parse("06/11/2015 00:00:00", DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));

        DateTime dt = DateTime.parse(startDate, DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss"));
        DateTime start = new LocalDateTime(dt).toDateTime(DateTimeZone.UTC);

        long difference = now.getMillis() - start.getMillis();
        long dayInMs = 3600000 * 24;

        double days = (double) difference / dayInMs;

        return days;

    }
    public static String msToHMS(long millis){

        String hms = "";
        int h,m,s;

        h = (int) TimeUnit.MILLISECONDS.toHours(millis);
        m = (int) TimeUnit.MILLISECONDS.toMinutes(millis) -  (int) TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        s = (int) TimeUnit.MILLISECONDS.toSeconds(millis) - (int) TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        if(h > 0){
            hms += h + "h ";
        }
        if(m > 0){
            hms += m + "m ";
        }
        if(s > 0){
            hms += s + "s ";
        }

        return hms;

    }
}

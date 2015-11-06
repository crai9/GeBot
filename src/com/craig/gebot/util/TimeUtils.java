package com.craig.gebot.util;

import java.util.concurrent.TimeUnit;

/**
 * Created by craig on 06/11/2015.
 */
public class TimeUtils {

    public static void main(String args[]){

        long ms = 10804000;

        System.out.print(msToHMS(ms));
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

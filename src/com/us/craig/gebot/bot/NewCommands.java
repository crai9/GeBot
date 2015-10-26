package com.us.craig.gebot.bot;

import com.us.craig.gebot.models.VOS;
import com.us.craig.gebot.util.HttpUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.google.gson.*;

import java.text.NumberFormat;
import java.util.Locale;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by craig on 20/10/2015.
 */

public class NewCommands {

    public static void main(String[] args){

        System.out.print(getRsTime());

    }

    public static String getTimeTillWbs(){

        long millis = (25200 - ((new Date().getTime() / 1000 - 1376222417) % 25200)) * 1000;

        return String.format("%02d hours, %02d min, %02d sec until next warbands.",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public static String getActiveVos(){
        String activeVos = "";
        String clan1 = "";
        String clan2 = "";
        String apiUrl = "https://fdcvos.herokuapp.com/api/tweets/findOne?filter={%22order%22:%20%22timestamp_ms%20DESC%22}";

        String json = HttpUtils.getTextFromUrl(apiUrl);

        Gson gson = new Gson();

        VOS v = gson.fromJson(json, VOS.class);

        clan1 = v.getText().split(" ")[9];
        clan2 = v.getText().split(" ")[11];
        activeVos = clan1 + " & " + clan2;

        return "Current VoS: " + activeVos;
    }

    public static String getRuneDate(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy hh:mm:ss");
        Date now = new Date();
        Date start = new Date();

        try {
            start = simpleDateFormat.parse("27/2/2002 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long different = now.getTime() - start.getTime();

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;

        return "The current runedate is: " + String.valueOf(elapsedDays) + ".";

    }

    public static String getTimeTillReset(){

        DateTime now = DateTime.now( DateTimeZone.UTC );
        long millis = new Duration( now , now.plusDays( 1 ).withTimeAtStartOfDay() ).getMillis();

        return String.format("%02d hours, %02d min, %02d sec until daily reset.",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public static String getRsTime(){

        final Date currentTime = new Date();
        final SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss a");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return "RS time is: " + sdf.format(currentTime) + ".";

    }

    public static String getPlayerCount(String game){

        int count = -1;
        String output = "";
        String apiUrl = "http://www.runescape.com/player_count.js?varname=iPlayerCount&callback=jQuery000000000000000_0000000000&_=0";
        String oldschoolUrl = "http://oldschool.runescape.com/";

        switch (game.toLowerCase()){

            case "rs3":

                String response = HttpUtils.getTextFromUrl(apiUrl);
                int totalcount = Integer.parseInt(response.split("\\)")[0].split("\\(")[1]);

                String response3 = HttpUtils.getTextFromUrl(oldschoolUrl);
                Document doc = Jsoup.parse(response3);
                String osText = doc.select(".player-count").text();
                int osCount = Integer.parseInt(osText.substring(0, osText.length() - 16).substring(20));

                count = totalcount - osCount;

                output = "RS3 player count: " + NumberFormat.getInstance(Locale.US).format(count);
                break;
            case "07":

                String response1 = HttpUtils.getTextFromUrl(oldschoolUrl);
                Document doc2 = Jsoup.parse(response1);
                String s = doc2.select(".player-count").text();
                count = Integer.parseInt(s.substring(0, s.length() -16).substring(20));

                output = "07 player count: " + NumberFormat.getInstance(Locale.US).format(count);
                break;
            case "both":
                String response2 = HttpUtils.getTextFromUrl(apiUrl);
                count = Integer.parseInt(response2.split("\\)")[0].split("\\(")[1]);

                String extras = "";
                if(count >= 100000){
                    extras = " PogChamp";
                }
                output = "Combined player count: " + NumberFormat.getInstance(Locale.US).format(count) + extras;

                break;
            default:
                output = "Usage: !count <rs3/07/both>";
                break;
        }

        return output;
    }

}

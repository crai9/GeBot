package com.us.craig.gebot.bot;

import com.us.craig.gebot.util.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by craig on 20/10/2015.
 */
public class NewCommands {

    public static void main(String[] args){

        System.out.print(playerCount("RS3"));

    }

    public static String playerCount(String game){

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

package com.us.craig.gebot.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by craig on 19/10/2015.
 */


public class ScrapingUtils {

    public static void main(String[] args) throws InterruptedException {

        int start = 0;
        int max = 71;
        String baseUrl = "http://runeapps.org/apps/ge/browse.php?page=";
        long sleep = 500;

        for(int index = start; index < max + 1 ; index++){

            System.out.print("Scraping page " + index + "\n");

            String html = HttpUtils.getTextFromUrl(baseUrl + index);

            Document doc = Jsoup.parse(html);
            Elements items= doc.select(".item");

            for(Element i : items){
                String itemName = i.children().get(1).text();
                int id = Integer.parseInt(i.children().get(2).text());
                String trade = i.attr("title").split("\r")[1].split(":")[0];

                //System.out.println("Page: " + index + ", itemID: " + id + ", itemName: " + itemName + ", Tradeable: " + trade + "\n");
                DBUtil.addItem(id, itemName, index, trade);

            }

            Thread.sleep(sleep);
        }




    }

}

package com.craig.gebot.util;

/**
 * Created by craig on 04/10/2015.
 */

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {


    public static void main(String[] args){

    }

    public static String getTextFromUrl(String site) {

        try {

            URL url = new URL(site);

            return GenUtils.convertStreamToString(url.openStream());


        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
        return "Not available";

    }

}

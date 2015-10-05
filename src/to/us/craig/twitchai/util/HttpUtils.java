package to.us.craig.twitchai.util;

/**
 * Created by craig on 04/10/2015.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtils {


    public static void main(String[] args){
        System.out.print(getTextFromUrl("http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=21787"));
    }

    public static String getTextFromUrl(String site) {

        try {

            URL url = new URL(site);

            // read text returned by server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            return line;
        }
        catch (MalformedURLException e) {
            System.out.println("Malformed URL: " + e.getMessage());
        }
        catch (IOException e) {
            System.out.println("I/O Error: " + e.getMessage());
        }
        return null;

    }

}

package to.us.craig.twitchai.util;

/**
 * Created by craig on 02/10/2015.
 */
public class PcUtils {

    static String baseURL = "http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=";

    public static String getItemPc(String itemName){

        int id = DBUtil.searchForItemId(itemName);
        String json = HttpUtils.getTextFromUrl(baseURL + id);

        return json;
    }

}

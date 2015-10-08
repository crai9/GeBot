package to.us.craig.twitchai.util;

/**
 * Created by craig on 02/10/2015.
 */

import com.google.gson.*;
import to.us.craig.twitchai.models.Holder;

public class PcUtils {

    static String baseURL = "http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=";

    public static int getItemPc(String itemName){

        int id = DBUtil.searchForItemId(itemName);
        String json = HttpUtils.getTextFromUrl(baseURL + id);

        Gson gson = new Gson();

        Holder h = gson.fromJson(json, Holder.class);

        int price = h.getItem().getCurrent().getPrice();
        LogUtils.logMsg(price);

        return price;
    }

}

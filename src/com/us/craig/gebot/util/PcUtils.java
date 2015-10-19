package com.us.craig.gebot.util;

/**
 * Created by craig on 02/10/2015.
 */

import com.google.gson.*;
import com.us.craig.gebot.models.Holder;
import com.us.craig.gebot.models.Item;

public class PcUtils {

    static String baseURL = "http://services.runescape.com/m=itemdb_rs/api/catalogue/detail.json?item=";

    public static void main(String[] args){

        LogUtils.logMsg(getItemPc("cannonball"));

    }

    public static String getItemPc(String itemName){

        int id = DBUtil.searchForItemId(itemName);
        String json = HttpUtils.getTextFromUrl(baseURL + id);

        LogUtils.logMsg(json);

        Gson gson = new Gson();

        Item i = gson.fromJson(json, Holder.class).getItem();

        String price = i.getCurrent().getPrice();

        //int price = 500007000;

        LogUtils.logMsg(price);

        return price;
    }

}

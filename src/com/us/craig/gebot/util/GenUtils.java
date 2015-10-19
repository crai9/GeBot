package com.us.craig.gebot.util;

import static com.us.craig.gebot.util.LogUtils.logMsg;
import static com.us.craig.gebot.util.LogUtils.logErr;

public class GenUtils
{

    public static void exit(int i)
    {
        if (i == 0)
        {
            logMsg("Exiting program with error code: " + i);
        }
        else
        {
            logErr("Exiting program with error code: " + i);
        }
        System.exit(i);
    }

    public static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}

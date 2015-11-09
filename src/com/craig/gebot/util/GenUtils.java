package com.craig.gebot.util;

import static com.craig.gebot.util.LogUtils.logMsg;
import static com.craig.gebot.util.LogUtils.logErr;

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

    public static String combine(String[] s, String glue)
    {
        int k = s.length;
        if ( k == 0 )
        {
            return null;
        }
        StringBuilder out = new StringBuilder();
        out.append( s[0] );
        for ( int x=1; x < k; ++x )
        {
            out.append(glue).append(s[x]);
        }
        return out.toString();
    }

}

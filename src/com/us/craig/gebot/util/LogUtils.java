package com.us.craig.gebot.util;

import static com.us.craig.gebot.util.Globals.*;
import static com.us.craig.gebot.util.FileUtils.*;

public class LogUtils
{

    public static void logMsg(String msg)
    {
        System.out.print("[LOG]: " + msg + "\n");
        writeToTextFile("data", "/log_" + g_dateformat.format(g_date) + ".txt", g_datetimeformat.format(g_date) + ": " + msg);
    }

    public static void logMsg(int msg)
    {
        System.out.print("[LOG]: " + msg + "\n");
        writeToTextFile("data", "/log_" + g_dateformat.format(g_date) + ".txt", g_datetimeformat.format(g_date) + ": " + msg);
    }

    public static void logMsg(String directory, String filename, String msg)
    {
        System.out.print("[LOG]: " + msg + "\n");
        writeToTextFile(directory, filename + "_" + g_dateformat.format(g_date) + "_LOG.txt", g_datetimeformat.format(g_date) + ": " + msg);
    }

    public static void logErr(String err)
    {
        System.err.print("[ERR]: " + err + "\n");
        writeToTextFile("data", "/err_" + g_dateformat.format(g_date) + ".txt", g_datetimeformat.format(g_date) + ": " + err);
    }

    public static void logErr(String directory, String filename, String err)
    {
        System.err.print("[ERR]: " + err + "\n");
        writeToTextFile(directory, filename + "_" + g_dateformat.format(g_date) + "_ERR.txt", g_datetimeformat.format(g_date) + ": " + err);
    }

}

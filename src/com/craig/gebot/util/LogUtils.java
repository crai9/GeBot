package com.craig.gebot.util;

public class LogUtils
{

    public static void logMsg(String msg)
    {
        System.out.print("[LOG]: " + msg + "\n");
        FileUtils.writeToTextFile("data", "/log_" + Globals.g_dateformat.format(Globals.g_date) + ".txt", Globals.g_datetimeformat.format(Globals.g_date) + ": " + msg);
    }

    public static void logMsg(int msg)
    {
        System.out.print("[LOG]: " + msg + "\n");
        FileUtils.writeToTextFile("data", "/log_" + Globals.g_dateformat.format(Globals.g_date) + ".txt", Globals.g_datetimeformat.format(Globals.g_date) + ": " + msg);
    }

    public static void logMsg(String directory, String filename, String msg)
    {
        System.out.print("[LOG]: " + msg + "\n");
        FileUtils.writeToTextFile(directory, filename + "_" + Globals.g_dateformat.format(Globals.g_date) + "_LOG.txt", Globals.g_datetimeformat.format(Globals.g_date) + ": " + msg);
    }

    public static void logErr(String err)
    {
        System.err.print("[ERR]: " + err + "\n");
        FileUtils.writeToTextFile("data", "/err_" + Globals.g_dateformat.format(Globals.g_date) + ".txt", Globals.g_datetimeformat.format(Globals.g_date) + ": " + err);
    }

    public static void logErr(String directory, String filename, String err)
    {
        System.err.print("[ERR]: " + err + "\n");
        FileUtils.writeToTextFile(directory, filename + "_" + Globals.g_dateformat.format(Globals.g_date) + "_ERR.txt", Globals.g_datetimeformat.format(Globals.g_date) + ": " + err);
    }

}

package com.craig.gebot.util;

import static com.craig.gebot.util.LogUtils.logMsg;
import static com.craig.gebot.util.LogUtils.logErr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfUtils
{

    public static void init()
    {
        File f = new File("data/config.cfg");
        if (f.exists() && !f.isDirectory())
        {
            load();
        }
        else
        {
            create();
            init();
        }
    }

    public static void create()
    {
        Properties p = new Properties();
        OutputStream o = null;
        try
        {
            o = new FileOutputStream("data/config.cfg");

            // Set each variable
            p.setProperty("g_debug", "false");
            p.setProperty("g_bot_reqMembership", "true");
            p.setProperty("g_bot_reqCommands", "true");
            p.setProperty("g_bot_reqTags", "false");
            p.setProperty("g_bot_name", "GeBot");
            p.setProperty("g_bot_oauth", "youroauth");
            p.setProperty("g_bot_chan", "#IllusionAI");

            // Store the variables
            p.store(o, null);

            // Close the outputstream object
            o.close();

            LogUtils.logMsg("config.cfg" + " Created succesfully!");
        } catch (IOException e)
        {
            LogUtils.logErr("Couldn't create the main configuration file, closing program...");
            GenUtils.exit(1);
        }
    }

    public static void load()
    {
        Properties p = new Properties();
        InputStream i = null;
        try
        {
            i = new FileInputStream("data/config.cfg");

            // Load the file
            p.load(i);

            // Get the properties and set the config variables
            Globals.g_debug = Boolean.valueOf(p.getProperty("g_debug"));
            Globals.g_bot_reqMembership = Boolean.valueOf(p.getProperty("g_bot_reqMembership"));
            Globals.g_bot_reqCommands = Boolean.valueOf(p.getProperty("g_bot_reqCommands"));
            Globals.g_bot_reqTags = Boolean.valueOf(p.getProperty("g_bot_reqTags"));
            Globals.g_bot_name = String.valueOf(p.getProperty("g_bot_name"));
            Globals.g_bot_oauth = String.valueOf(p.getProperty("g_bot_oauth"));
            Globals.g_bot_chan = String.valueOf(p.getProperty("g_bot_chan"));

            // Close the inputstream object
            i.close();

            LogUtils.logMsg("config.cfg" + " loaded succesfully!");
        } catch (IOException e)
        {
            LogUtils.logErr("Couldn't load the main configuration file, closing program...");
            GenUtils.exit(1);
        }
    }

}
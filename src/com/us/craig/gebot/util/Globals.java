package com.us.craig.gebot.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.us.craig.gebot.bot.TwitchUser;

public class Globals
{

    // Config
    public static boolean          g_debug;
    public static boolean          g_bot_reqMembership;
    public static boolean          g_bot_reqCommands;
    public static boolean          g_bot_reqTags;
    public static String           g_bot_name;
    public static String           g_bot_oauth;
    public static String           g_bot_chan;
    public static String           g_bot_desc       = "A mildly useful bot for getting information about RS. !help for commands. ";
    public static String           g_bot_version    = "GeBot v0.3. ";
    public static String           g_lib_version    = "PircBot 1.5.0 ";

    // Time & Date
    public static DateFormat       g_datetimeformat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    public static DateFormat       g_dateformat     = new SimpleDateFormat("dd.MM.yyyy");
    public static DateFormat       g_timeformat     = new SimpleDateFormat("HH:mm:ss");
    public static Date             g_date           = new Date();

    // Global variables
    public static final String     g_commands_user  = "!help !info !date !time !slots !count !pc";
    public static final String     g_commands_op    = "!permit";
    public static final String     g_commands_mod   = "!joinchan !partchan !addchan !delchan";
    public static final String     g_commands_admin = "!addmod !delmod ";
    public static final String     g_commands_bot   = "!help !join !leave";
    public static final String[]   g_emotes_faces   = { "4Head", "Kappa", "Keepo" };

    // Server messages
    public static final String     g_server_memreq  = "CAP REQ :twitch.tv/membership";
    public static final String     g_server_memans  = ":tmi.twitch.tv CAP * ACK :twitch.tv/membership";
    public static final String     g_server_cmdreq  = "CAP REQ :twitch.tv/commands";
    public static final String     g_server_cmdans  = ":tmi.twitch.tv CAP * ACK :twitch.tv/commands";
    public static final String     g_server_tagreq  = "CAP REQ :twitch.tv/tags";
    public static final String     g_server_tagans  = ":tmi.twitch.tv CAP * ACK :twitch.tv/tags";

    // Java objects
    public static final TwitchUser g_nulluser       = new TwitchUser("null", "");

}

package com.craig.gebot.bot;

import static com.craig.gebot.util.LogUtils.logMsg;
import static com.craig.gebot.util.LogUtils.logErr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.craig.gebot.util.*;
import com.craig.gebot.util.LogUtils;
import org.jibble.pircbot.IrcException;
import org.jibble.pircbot.PircBot;
import org.jibble.pircbot.User;

import com.craig.gebot.util.FileUtils;

public class GeBot extends PircBot
{

    private float                    m_cycleTime;
    private float                    m_cmdTime;
    private boolean                  m_hasMembership;
    private boolean                  m_hasCommands;
    private boolean                  m_hasTags;
    private ArrayList<TwitchUser>    m_moderators;
    private ArrayList<TwitchChannel> m_channels;

    public GeBot()
    {
        m_cycleTime = 0.0f;
        m_cmdTime = 0.0f;
        m_hasMembership = false;
        m_hasCommands = false;
        m_hasTags = true;
        m_moderators = new ArrayList<TwitchUser>();
        m_channels = new ArrayList<TwitchChannel>();

        setName(Globals.g_bot_name);
        setVersion(Globals.g_lib_version);
        setVerbose(false);
    }

    public void init_twitch()
    {
        LogUtils.logMsg("Loading all registered GeBot moderators...");
        ArrayList<String> loadedModerators = FileUtils.readTextFile("data/moderators.txt");
        for (String m : loadedModerators)
        {
            String[] m_split = m.split(" ");
            TwitchUser newmod = new TwitchUser(m_split[0], m_split[1]);
            LogUtils.logMsg("Added a GeBot moderator (" + newmod + ") to m_moderators");
            m_moderators.add(newmod);
        }

        LogUtils.logMsg("Attempting to connect to irc.twitch.tv...");
        try
        {
            connect("irc.twitch.tv", 6667, Globals.g_bot_oauth);
        } catch (IOException | IrcException e)
        {
            LogUtils.logErr(e.getStackTrace().toString());
            GenUtils.exit(1);
        }

        if (Globals.g_bot_reqMembership)
        {
            LogUtils.logMsg("Requesting twitch membership capability for NAMES/JOIN/PART/MODE messages...");
            sendRawLine(Globals.g_server_memreq);
        }
        else
        {
            LogUtils.logMsg("Membership request is disabled!");
            m_hasMembership = true;
        }

        if (Globals.g_bot_reqCommands)
        {
            LogUtils.logMsg("Requesting twitch commands capability for NOTICE/HOSTTARGET/CLEARCHAT/USERSTATE messages... ");
            sendRawLine(Globals.g_server_cmdreq);
        }
        else
        {
            LogUtils.logMsg("Commands request is disabled!");
            m_hasCommands = true;
        }

        if (Globals.g_bot_reqTags)
        {
            LogUtils.logMsg("Requesting twitch tags capability for PRIVMSG/USERSTATE/GLOBALUSERSTATE messages... ");
            sendRawLine(Globals.g_server_tagreq);
        }
        else
        {
            LogUtils.logMsg("Tags request is disabled!");
            m_hasTags = true;
        }
    }

    public void init_channels()
    {
        LogUtils.logMsg("Attempting to join all registered channels...");
        ArrayList<String> loadedChannels = FileUtils.readTextFile("data/channels.txt");
        for (String c : loadedChannels)
        {
            if (!c.startsWith("#"))
            {
                c = "#" + c;
            }
            joinToChannel(c);
        }
    }

    public void joinToChannel(String channel)
    {
        LogUtils.logMsg("Attempting to join channel " + channel);
        joinChannel(channel);
        m_channels.add(new TwitchChannel(channel));
    }

    public void partFromChannel(String channel)
    {
        LogUtils.logMsg("Attempting to leave channel " + channel);
        partChannel(channel);
        m_channels.remove(getTwitchChannel(channel));
    }

    public void addChannel(String channel, String sender, String addChan)
    {
        ArrayList<String> addchan_channels = FileUtils.readTextFile("data/channels.txt");
        if (addchan_channels.size() <= 0 || !addchan_channels.contains(addChan))
        {
            LogUtils.logMsg("Joining a new channel: " + addChan);
            sendTwitchMessage(channel, "Joining channel: " + addChan);
            FileUtils.writeToTextFile("data/", "channels.txt", addChan);
            joinToChannel(addChan);
        }
        else
        {
            LogUtils.logErr("Failed to join a new channel: " + addChan);
            sendTwitchMessage(channel, "That channel is already registered!");
        }
        return;
    }

    public void delChannel(String channel, String sender, String delChan)
    {
        if (!Arrays.asList(getChannels()).contains(delChan))
        {
            LogUtils.logErr("Can't delete channel " + delChan + " from the global channels list because it isn't in the joined channels list!");
            return;
        }
        LogUtils.logMsg(sender + " Leaving channel: " + delChan);
        sendTwitchMessage(channel, sender + " Leaving channel: " + delChan);
        partFromChannel(delChan);
        FileUtils.removeFromTextFile("data", "/channels.txt", delChan);
    }

    public void sendTwitchMessage(String channel, String message)
    {
        TwitchChannel twitch_channel = getTwitchChannel(channel);
        TwitchUser twitch_user = twitch_channel.getUser(Globals.g_bot_name);

        if (twitch_user == null)
        {
            twitch_user = Globals.g_nulluser;
        }

        if (twitch_user.isOperator())
        {
            if (twitch_channel.getCmdSent() <= 48)
            {
                twitch_channel.setCmdSent(twitch_channel.getCmdSent() + 1);
                sendMessage(channel, message);
                LogUtils.logMsg(channel + " | " + Globals.g_bot_name + " | " + message);
            }
            else
            {
                LogUtils.logErr("Cannot send a message to channel (" + twitch_channel + ")! 100 Messages per 30s limit nearly exceeded! (" + twitch_channel.getCmdSent() + ")");
            }
        }
        else
        {
            if (twitch_channel.getCmdSent() <= 16)
            {
                twitch_channel.setCmdSent(twitch_channel.getCmdSent() + 1);
                sendMessage(channel, message);
                LogUtils.logMsg(channel + " | " + Globals.g_bot_name + " | " + message);
            }
            else
            {
                LogUtils.logErr("Cannot send a message to channel (" + twitch_channel + ")! 20 Messages per 30s limit nearly exceeded! (" + twitch_channel.getCmdSent() + ")");
            }
        }
    }

    @Override
    public void handleLine(String line)
    {
        //logMsg("HL | " + line);

        super.handleLine(line);

        if (!isInitialized())
        {
            if (line.equals(Globals.g_server_memans))
            {
                m_hasMembership = true;
            }

            if (line.equals(Globals.g_server_cmdans))
            {
                m_hasCommands = true;
            }

            if (line.equals(Globals.g_server_tagans))
            {
                m_hasTags = true;
            }
        }

        if (line.contains(":jtv "))
        {
            line = line.replace(":jtv ", "");
            String[] line_array = line.split(" ");

            if (line_array[0].equals("MODE") && line_array.length >= 4)
            {
                onMode(line_array[1], line_array[3], line_array[3], "", line_array[2]);
            }
        }
    }

    @Override
    public void onUserList(String channel, User[] users)
    {
        super.onUserList(channel, users);

        TwitchChannel twitch_channel = getTwitchChannel(channel);

        if (twitch_channel == null)
        {
            LogUtils.logErr("Error on USERLIST, channel (" + channel + ") doesn't exist!");
            return;
        }

        for (User u : users)
        {
            if (twitch_channel.getUser(u.getNick()) == null)
            {
                TwitchUser twitch_mod = getOfflineModerator(u.getNick());
                String prefix = "";
                if (twitch_mod != null)
                {
                    prefix = twitch_mod.getPrefix();
                }
                TwitchUser user = new TwitchUser(u.getNick(), prefix);
                twitch_channel.addUser(user);
                LogUtils.logMsg("Adding (" + user.getName() + ") to (" + twitch_channel.getName() + ")");
            }
        }
    }

    @Override
    public void onJoin(String channel, String sender, String login, String hostname)
    {
        super.onJoin(channel, sender, login, hostname);

        TwitchChannel twitch_channel = getTwitchChannel(channel);
        TwitchUser twitch_user = twitch_channel.getUser(sender);
        TwitchUser twitch_mod = getOfflineModerator(sender);

        if (twitch_channel != null && twitch_user == null)
        {
            String prefix = "";
            if (twitch_mod != null)
            {
                prefix = twitch_mod.getPrefix();
            }
            TwitchUser user = new TwitchUser(sender, prefix);
            twitch_channel.addUser(user);
            LogUtils.logMsg("Adding (" + user.getName() + ") to (" + twitch_channel.getName() + ")");
        }
    }

    @Override
    public void onPart(String channel, String sender, String login, String hostname)
    {
        super.onPart(channel, sender, login, hostname);

        TwitchChannel twitch_channel = getTwitchChannel(channel);
        TwitchUser twitch_user = twitch_channel.getUser(sender);

        if (twitch_channel != null && twitch_user != null)
        {
            twitch_channel.delUser(twitch_user);
            LogUtils.logMsg("Removing (" + twitch_user.getName() + ") from (" + twitch_channel.getName() + ")");
        }
    }

    @Override
    public void onMode(String channel, String sourceNick, String sourceLogin, String sourceHostname, String mode)
    {
        super.onMode(channel, sourceNick, sourceLogin, sourceHostname, mode);

        TwitchChannel twitch_channel = getTwitchChannel(channel);
        TwitchUser twitch_user = twitch_channel.getUser(sourceNick);

        if (twitch_user == null)
        {
            LogUtils.logErr("Error on MODE, cannot find (" + twitch_user + ") from channel (" + twitch_channel.toString() + ")");
            return;
        }

        if (mode.equals("+o"))
        {
            LogUtils.logMsg("Adding +o MODE for user (" + twitch_user + ") in channel (" + twitch_channel.toString() + ")");
            twitch_user.addPrefixChar("@");
        }
        else if (mode.equals("-o"))
        {
            LogUtils.logMsg("Adding -o MODE for user (" + twitch_user + ") in channel (" + twitch_channel.toString() + ")");
            twitch_user.delPrefixChar("@");
        }
    }

    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message)
    {
        LogUtils.logMsg("data/channels/" + channel, "/onMessage", channel + " | " + sender + " | " + message);

        TwitchChannel twitch_channel = getTwitchChannel(channel);
        TwitchUser twitch_user = twitch_channel.getUser(sender);

        /*
         * Handle all chat commands
         */

        if (message.startsWith("!"))
        {

            if (twitch_user == null)
            {
                LogUtils.logErr("Error, (" + sender + ") doesn't exist!");
                twitch_user = Globals.g_nulluser;
            }

//            if (message.length() > 3)
//            {
//                if (twitch_user.getCmdTimer() > 0)
//                {
//                    if (twitch_user.getCmdTimer() > 10 && twitch_channel.getCmdSent() < 32)
//                    {
//                        sendTwitchMessage(channel, twitch_user + " Please wait " + twitch_user.getCmdTimer() + " seconds before sending a new command.");
//                    }
//                    twitch_user.setCmdTimer(twitch_user.getCmdTimer() + 5);
//                    return;
//                }
//                else
//                {
//                    if (!twitch_user.getName().equals("null"))
//                    {
//                        twitch_user.setCmdTimer(5);
//                    }
//                }
//            }

            message = message.replace("!", "");
            String[] msg_array = message.split(" ");
            String msg_command = msg_array[0];
            String user_sender = sender;
            String user_target;
            String chan_sender = channel;
            String chan_target;
            float time;
            long timeStart, timeEnd;

            timeStart = System.nanoTime();

            /*
             * Commands available on the bot's own channel
             */

            if (channel.equals(Globals.g_bot_chan))
            {
                switch (msg_command.toLowerCase())
                {
                    case "help":
                        sendTwitchMessage(channel, "List of available commands on this channel: " + Globals.g_commands_bot);
                        break;
                    case "join":
                        addChannel(channel, Globals.g_bot_name, user_sender);
                        break;
                    case "leave":
                        delChannel(channel, Globals.g_bot_name, user_sender);
                        break;
                }
            }

            /*
             * Commands available on all channels
             */

            switch (msg_command.toLowerCase())
            {

            /*
             * Normal channel user commands below
             */
                case "help":
                    String help_text = "List of available commands to you: " + Globals.g_commands_user;

                    sendTwitchMessage(channel, help_text);
                    break;

                case "date":
                    sendTwitchMessage(channel, Globals.g_dateformat.format(Globals.g_date).replace('.','/'));
                    break;

                case "time":
                    sendTwitchMessage(channel, Commands.getRsTime());
                    break;

                case "info":
                    sendTwitchMessage(channel, Globals.g_bot_desc + Globals.g_bot_version);
                    break;

                case "reset":
                    sendTwitchMessage(channel, Commands.getTimeTillReset());
                    break;

                case "warbands":
                    sendTwitchMessage(channel, Commands.getTimeTillWbs());
                    break;

                case "_debug":
                    sendTwitchMessage(channel, Commands.getDebugInfo(getTwitchChannels().size()));
                    break;

                case "runedate":
                    sendTwitchMessage(channel, Commands.getRuneDate());
                    break;

                case "araxxor":
                    sendTwitchMessage(channel, Commands.getArraxorInfo());
                    break;

                case "clear":
                    sendTwitchMessage(channel, "/clear");
                    break;

                case "slots": // Half-assed simple slots game. :D
                    int num1 = (int) (Math.random() * Globals.g_emotes_faces.length);
                    int num2 = (int) (Math.random() * Globals.g_emotes_faces.length);
                    int num3 = (int) (Math.random() * Globals.g_emotes_faces.length);
                    String slots = Globals.g_emotes_faces[num1] + " | " + Globals.g_emotes_faces[num2] + " | " + Globals.g_emotes_faces[num3];
                    sendTwitchMessage(channel, slots);
                    if (num1 == num2 && num2 == num3)
                    {
                        sendTwitchMessage(channel, "And we have a new winner! " + sender + " Just got their name on the slots legends list!");
                        FileUtils.writeToTextFile("data/", "slots.txt", Globals.g_datetimeformat.format(Globals.g_date) + " " + sender + ": " + slots);
                    }
                    break;


                case "broadcast":
                    if (!twitch_user.isAdmin())
                    {
                        break;
                    }

                    if (msg_array.length <= 1)
                    {
                        sendTwitchMessage(channel, "Usage: !broadcast message");
                        break;
                    }

                    String broadcast_message = message.replace(msg_array[0], "");

                    for (TwitchChannel c : m_channels)
                    {
                        LogUtils.logMsg("Sending a broadcast message to channel (" + c + ") Message: " + broadcast_message);
                        sendTwitchMessage(c.getName(), "System broadcast message: " + broadcast_message);
                    }
                    break;
                case "vos":

                    sendTwitchMessage(channel, Commands.getActiveVos());

                    break;
                case "count":


                    String game = "";
                    if (msg_array.length <= 1){
                        game = "Kappa";
                    } else {
                        game = msg_array[1];
                    }

                    sendTwitchMessage(channel, Commands.getPlayerCount(game));

                    break;
                case "pc":

                    String pcMessage = message.replace("pc ", "");

                    if(msg_array.length <= 1){
                        sendTwitchMessage(channel, "Usage: !pc <item(exact spelling)>");
                        break;
                    }

                    String price = PcUtils.getItemPc(pcMessage);

                    sendTwitchMessage(channel, "The price of " + pcMessage + " is: " + price + " gp.");

                    break;
            }

            //timeEnd = System.nanoTime();
            //time = (float) (timeEnd - timeStart) / 1000000.0f;

            //setCmdTime(getCmdTime() * 0.1f + time * 0.9f);
        }
    }

    @Override
    public void onPrivateMessage(String sender, String login, String hostname, String message)
    {
        //logMsg("data", "/privmsg", sender + " | " + message);
    }

    public ArrayList<TwitchChannel> getTwitchChannels()
    {
        return m_channels;
    }

    public TwitchChannel getTwitchChannel(String name)
    {
        TwitchChannel result = null;

        for (TwitchChannel tc : m_channels)
        {
            if (tc.getName().equals(name))
            {
                result = tc;
                break;
            }
        }

        return result;
    }

    public ArrayList<TwitchUser> getAllUsers()
    {
        ArrayList<TwitchUser> result = new ArrayList<TwitchUser>();

        for (TwitchChannel tc : m_channels)
        {
            result.addAll(tc.getUsers());
        }

        return result;
    }

    public ArrayList<TwitchUser> getAllOperators()
    {
        ArrayList<TwitchUser> result = new ArrayList<TwitchUser>();

        for (TwitchChannel tc : m_channels)
        {
            result.addAll(tc.getOperators());
        }

        return result;
    }

    public ArrayList<TwitchUser> getOnlineModerators()
    {
        ArrayList<TwitchUser> result = new ArrayList<TwitchUser>();

        for (TwitchChannel tc : m_channels)
        {
            result.addAll(tc.getModerators());
        }

        return result;
    }

    public ArrayList<TwitchUser> getOfflineModerators()
    {
        return m_moderators;
    }

    public TwitchUser getOfflineModerator(String nick)
    {
        TwitchUser result = null;

        for (TwitchUser tu : m_moderators)
        {
            if (tu.getName().equals(nick))
            {
                result = tu;
            }
        }

        return result;
    }

    public float getCycleTime()
    {
        return m_cycleTime;
    }

    public void setCycleTime(float cycleTime)
    {
        m_cycleTime = cycleTime;
    }

    public float getCmdTime()
    {
        return m_cmdTime;
    }

    public void setCmdTime(float cmdTime)
    {
        m_cmdTime = cmdTime;
    }

    public boolean isInitialized()
    {
        return m_hasMembership & m_hasCommands & m_hasTags;
    }
}

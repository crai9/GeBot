package com.us.craig.gebot;

import static com.us.craig.gebot.util.Globals.*;
import static com.us.craig.gebot.util.LogUtils.logMsg;
import static com.us.craig.gebot.util.LogUtils.logErr;
import static com.us.craig.gebot.util.GenUtils.exit;
import com.us.craig.gebot.bot.TwitchChannel;
import com.us.craig.gebot.bot.TwitchUser;
import com.us.craig.gebot.bot.GeBot;
import com.us.craig.gebot.util.ConfUtils;
import com.us.craig.gebot.util.FileUtils;

public class Main
{

    public static void main(String[] args)
    {
        FileUtils.directoryExists("data");
        FileUtils.directoryExists("data/channels");
        ConfUtils.init();
        GeBot twitchai = new GeBot();
        twitchai.init_twitch();

        int init_time = 5;
        while (!twitchai.isInitialized())
        {
            init_time--;
            try
            {
                logMsg("Waiting for twitch member/cmd/tag responses... " + init_time);
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        if (!twitchai.isInitialized())
        {
            logErr("Failed com receive twitch member/cmd/tag permissions!");
            exit(1);
        }

        twitchai.init_channels();

        float time;
        long timeStart, timeEnd;

        while (twitchai.isConnected())
        {
            timeStart = System.nanoTime();
            g_date.setTime(System.currentTimeMillis());

            for (TwitchChannel c : twitchai.getTwitchChannels())
            {
                if (c.getCmdSent() > 0)
                {
                    c.setCmdSent(c.getCmdSent() - 1);
                }
            }

            for (TwitchUser u : twitchai.getAllUsers())
            {
                if (u.getCmdTimer() > 0)
                {
                    u.setCmdTimer(u.getCmdTimer() - 1);
                }
            }

            timeEnd = System.nanoTime();
            time = (float) (timeEnd - timeStart) / 1000000.0f;

            twitchai.setCycleTime(time);

            /*
             * Main loop ticks only once per second.
             */
            try
            {
                if (time < 1000.0f)
                {
                    Thread.sleep((long) (1000.0f - time));
                }
                else
                {
                    logErr("Warning! Main thread cycle time is longer than a second! Skipping sleep! Cycle-time: " + time);
                }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}

package com.craig.gebot;

import static com.craig.gebot.util.LogUtils.logMsg;
import static com.craig.gebot.util.LogUtils.logErr;

import com.craig.gebot.bot.TwitchChannel;
import com.craig.gebot.util.FileUtils;
import com.craig.gebot.util.GenUtils;
import com.craig.gebot.util.Globals;
import com.craig.gebot.util.LogUtils;
import com.craig.gebot.bot.TwitchUser;
import com.craig.gebot.bot.GeBot;
import com.craig.gebot.util.ConfUtils;
import com.craig.gebot.util.ScrapingUtils;

public class Main
{

    public static void main(String[] args) throws InterruptedException {

        if(args.length > 1){

            if(args[0].equals("scrape")){
                int pages;
                try{
                    pages = Integer.parseInt(args[1]);
                }catch (ArrayIndexOutOfBoundsException a){
                    pages = 71;
                }

                scrape(pages);
            }

        } else {

            twitch();

        }

        //twitch();

    }

    public static void scrape(int pages) throws InterruptedException {

        ScrapingUtils.scrape(pages);

    }

    public static void twitch(){

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
                LogUtils.logMsg("Waiting for twitch member/cmd/tag responses... " + init_time);
                Thread.sleep(1000);
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        if (!twitchai.isInitialized())
        {
            LogUtils.logErr("Failed com receive twitch member/cmd/tag permissions!");
            GenUtils.exit(1);
        }

        twitchai.init_channels();

        float time;
        long timeStart, timeEnd;

        while (twitchai.isConnected())
        {
            timeStart = System.nanoTime();
            Globals.g_date.setTime(System.currentTimeMillis());

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
                    LogUtils.logErr("Warning! Main thread cycle time is longer than a second! Skipping sleep! Cycle-time: " + time);
                }
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

}

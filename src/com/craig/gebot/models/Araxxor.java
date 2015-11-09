package com.craig.gebot.models;

import com.craig.gebot.util.TimeUtils;

import static com.craig.gebot.util.GenUtils.combine;
/**
 * Created by craig on 07/11/2015.
 */
public class Araxxor {

    private static String activePaths;
    private static int daysTillPathChange;
    private static String nextPaths;

    public static void main(String args[]){

        System.out.print("Active Paths: " + getActivePaths() + "\nIn " + getDaysTillPathChange() + " daily resets active paths will be: " + getNextPaths() );
    }

    public static int getDaysTillPathChange() {

        daysTillPathChange = (int) TimeUtils.daysSinceDate("05/08/2015 00:00:00"); //days since a path 2/3

        return (daysTillPathChange % 4) + 1;
    }

    public static void setDaysTillPathChange(int daysTillPathChange) {
        daysTillPathChange = daysTillPathChange;
    }

    public static String getActivePaths() {

        int days = (getDaysTillPathChange() % 12) + 1; //days till a 2/3?

        if(days <= 4){
            activePaths = "Spider minions & Acid pool";
        }
        else if(days <= 8){
            activePaths = "Spider minions & Darkness";
        }
        else if(days <= 12){
            activePaths = "Darkness & Acid pool";
        }

        return activePaths;
    }

    public static String getNextPaths() {

        int days = (getDaysTillPathChange() % 12) + 1; //days till a 2/3?

        if(days <= 4){
            nextPaths = "Darkness & Acid pool";
        }
        else if(days <= 8){
            nextPaths = "Spider minions & Acid Pool";
        }
        else if(days <= 12){
            nextPaths = "Darkness & Spider minions";
        }

        return nextPaths;
    }

    public static void setActivePaths(String activePath) {
        activePaths = activePath;
    }
}

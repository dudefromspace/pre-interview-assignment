package com.interview.rakuten.preinterviewassignment.utils;

public class RoundingUtil {

    public static String roundDuration(String sec) {
        if(sec.endsWith("s")){
            sec = sec.replace("s","");
        }
        int seconds = Integer.parseInt(sec);
        int roundedMinutes = seconds/60;
        int extraSeconds =  seconds%60;
        if(extraSeconds>0){
            roundedMinutes = roundedMinutes + 1;
        }
        return String.valueOf(roundedMinutes);
    }

    public static String roundVolume(String volInKB) {
        if(volInKB.endsWith("KB")){
            volInKB = volInKB.replace("KB","");
        }
        int volume = Integer.parseInt(volInKB);
        int roundedVolume = volume/1000;
        int extraVolume = volume%1000;
        if(extraVolume>0){
            roundedVolume = roundedVolume + 1;
        }
        return String.valueOf(roundedVolume);
    }
}

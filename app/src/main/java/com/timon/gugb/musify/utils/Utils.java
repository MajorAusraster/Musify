package com.timon.gugb.musify.utils;

import android.content.Context;

/**
 * Created by Timon on 02.03.2016.
 */
public class Utils {

    public static class Converter{

        public static String timeToDisplayForm(int seconds){
            int sek=seconds%60;
            int min=seconds/60;

            String smin="";
            String ssek="";

            if(min==0){
                smin="00";
            }else if(min<10){
                smin="0"+min;
            }else{
                smin=String.valueOf(min);
            }

            if(sek==0){
                ssek="00";
            }else if(sek<10){
                ssek="0"+sek;
            }else{
                ssek=String.valueOf(sek);
            }


            String dmy=smin+":"+ssek;
            return dmy;
        }

    }

}

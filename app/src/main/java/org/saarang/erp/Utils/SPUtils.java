package org.saarang.erp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ahammad on 16/06/15.
 */
public class SPUtils {

    private static String sp = "sharedPreferences";

    private static String spLatestPostDate = "latestPost";
    private static String spOldestPostDate = "LastPostDate";
    private static String spLastUpdateTime = "spLastUpdateTime";
    private static String spNotifLastUpdateTime = "spNotifLastUpdateTime";
    private static String spIfNewsFeedDownloaded = "ifNewsFeedDownloaded";

    public static void setLatestPostDate(Context context, String date){
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spLatestPostDate, date);
        editor.commit();
    }

    public static String getLatestPostDate(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getString(spLatestPostDate, "");
    }

    public static void setOldestPostDate(Context context, String date){
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spOldestPostDate, date);
        editor.commit();
    }

    public static String getOldestPostDate(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getString(spOldestPostDate, "");
    }


    public static void setNewsFeedDownloadedOnce(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(spIfNewsFeedDownloaded, true);
        editor.commit();
    }

    public static Boolean ifNewsFeedDownloaded(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getBoolean(spIfNewsFeedDownloaded, false);
    }

    public static void setLastUpdateTime(Context context, long timeMillis) {
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(spLastUpdateTime, timeMillis);
        editor.commit();
    }

    public static long getLastUpdateTime(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getLong(spLastUpdateTime, 0);
    }

    public static void setNotifLastUpdateTime(Context context, long timeMillis) {
        SharedPreferences preferences = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(spNotifLastUpdateTime, timeMillis);
        editor.commit();
    }

    public static long getNotifLastUpdateTime(Context context){
        SharedPreferences pref = context.getSharedPreferences(sp, Context.MODE_PRIVATE);
        return pref.getLong(spNotifLastUpdateTime, 0);
    }

}

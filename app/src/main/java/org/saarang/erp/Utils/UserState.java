package org.saarang.erp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import org.saarang.erp.Objects.ERPUser;

/**
 * Created by Moochi on 18-06-2015.
 */

//
public class UserState {

    /**
     * Class for setting and retrieving last user state from SharedPreferences
     * UserStates
     *      1    LoginActivity
     *      2    ProfilePictureActivity
     *      3    UpdateProfileActivity
     *      4    MainActivity
     */

    public  static String spLastActivity = "spLastActivity";

    public static void setLastActivity( Context context, int position){

        SharedPreferences preferences = context.getSharedPreferences(ERPUser.spUser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(spLastActivity,position);
        editor.commit();
    }

    public static int getLastActivity(Context context){
        SharedPreferences preferences = context.getSharedPreferences(ERPUser.spUser, Context.MODE_PRIVATE);
        return preferences.getInt(spLastActivity,1);

    }
}

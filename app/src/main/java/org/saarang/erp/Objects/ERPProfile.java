package org.saarang.erp.Objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;

/**
 * Created by Ahammad on 16/06/15.
 */
public class ERPProfile {

    private static String LOG_TAG="ERPProfile";
    public static String spUser = "spUser";
    public static String spName = "spName";
    public static String spToken = "sptoken";
    public static String spAlternateNumber = "spAlternateNumber";
    public static String spPhoneNumber = "spPhoneNumber";
    public static String spRollNumber = "spRollNumber";
    public static String spSummerLocation = "spSummerLocation";
    public static String spEmail = "spEmail";
    public static String spRoomNumber = "spRoomNumber";
    public static String spId="spId";
    public static String spProfilePic = "spProfilePic";
    public static String spProfilePicId = "spProfilePicId";
    public static String spWalls = "spWalls";


    String id, phoneNumber,alternateNumber, summerLocation, roomNumber, rollNumber, name;
    public ArrayList<PostParam> pData;
    public static void saveUser(Context context, JSONObject json){

        try {
            String token = json.getJSONObject("data").getString("token");
            JSONObject user = json.getJSONObject("data").getJSONObject("user");
            String id = user.getString("_id");
            String alternateNumber = user.getString("alternateNumber");
            String phoneNumber = user.getString("phoneNumber");
            String summerLocation = user.getString("summerLocation");
            String email = user.getString("email");
            String roomNumber = user.getString("roomNumber");
            String rollNumber = user.getString("rollNumber");
            String name = user.getString("name");

            SharedPreferences preferences = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(spName, name);
            editor.putString(spToken, token);
            editor.putString(spId, id);
            editor.putString(spAlternateNumber, alternateNumber);
            editor.putString(spPhoneNumber, phoneNumber);
            editor.putString(spRollNumber, rollNumber);
            editor.putString(spSummerLocation, summerLocation);
            editor.putString(spEmail, email);
            editor.putString(spRoomNumber, roomNumber);
            editor.commit();

        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

    }

    public static String getERPUserName(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spName, "");
    }

    public static void setUserProfilePic(Context context, String path){
        SharedPreferences preferences = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spProfilePic, path);
        editor.commit();
    }

    public static String getUserProfilePic(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spProfilePic, "");
    }

    public static void setUserProfilePicId(Context context, String path){
        SharedPreferences preferences = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spProfilePicId, path);
        editor.commit();
    }

    public static String getUserProfilePicId(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spProfilePicId, "");
    }

    public static String getERPUserToken(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spToken, "");
    }
    public static String getERPUserAlternateNumber(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spAlternateNumber, "");
    }
    public static String getERPUserPhoneNumber(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spPhoneNumber, "");
    }
    public static String getERPUserSummerLocation(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spSummerLocation, "");
    }
    public static String getERPUserEmail(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spEmail, "");
    }
    public static String getERPUserRoomNumber(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spRoomNumber, "");
    }
    public static String getERPUserId(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spId, "");
    }

    public static void setUserWalls(Context context, String walls){
        SharedPreferences preferences = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spWalls, walls);
        editor.commit();
    }

    public static String getUserWalls(Context context){
        SharedPreferences pref = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        return pref.getString(spWalls, "");
    }

    public static void saveUpdatedUser(Context context, ArrayList<PostParam> postData){

        SharedPreferences preferences = context.getSharedPreferences(spUser, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(spName, postData.get(0).getValue());
        //editor.putString(spToken, ERPProfile.getERPUserToken(context));
        // editor.putString(spId, ERPProfile.getERPUserId(context));
        editor.putString(spAlternateNumber, postData.get(3).getValue());
        editor.putString(spPhoneNumber, postData.get(2).getValue());
        // editor.putString(spRollNumber, rollNumber);
        editor.putString(spSummerLocation, postData.get(1).getValue());
       // editor.putString(spEmail, email);
        editor.putString(spRoomNumber, postData.get(4).getValue());
        editor.commit();
        Log.d(LOG_TAG,""+ editor);
    }





}

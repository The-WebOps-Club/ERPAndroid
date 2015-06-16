package org.saarang.erp.Objects;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Ahammad on 16/06/15.
 */
public class ERPUser {

    public static String spUser = "spUser";
    public static String spName = "spName";
    public static String spToken = "sptoken";
    public static String spAlternateNumber = "spAlternateNumber";
    public static String spPhoneNumber = "spPhoneNumber";
    public static String spRollNumber = "spRollNumber";
    public static String spSummerLocation = "spSummerLocation";
    public static String spEmail = "spEmail";
    public static String spRoomNumber = "spRoomNumber";

    String id, phoneNumber,alternateNumber, summerLocation, roomNumber, rollNumber, name;

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

}

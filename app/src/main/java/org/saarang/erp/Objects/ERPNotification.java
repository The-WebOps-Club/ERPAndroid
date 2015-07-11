package org.saarang.erp.Objects;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahammad on 11/07/15.
 */
public class ERPNotification {

    String _id, postId, action;
    ERPWall wall;
    ERPUser[] user;

    public static String TABLE_NAME = "ERPNotifications";

    public static String KEY_ROWID = "_id";
    public static String COLUMN_NOTIFID = "notifId";
    public static String COLUMN_POST_ID = "postId";
    public static String COLUMN_ACTION = "acton";
    public static String COLUMN_WALL = "wall";
    public static String COLUMN_USER = "user";

    public static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            KEY_ROWID + " INTEGER " + " PRIMARY KEY , " +
            COLUMN_POST_ID + " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE , " +
            COLUMN_NOTIFID + " TEXT NOT NULL , " +
            COLUMN_WALL + " TEXT  , " +
            COLUMN_ACTION + " TEXT NOT NULL , " +
            COLUMN_USER + " TEXT NOT NULL " +
            " );";

    public static String[] columns = {
        KEY_ROWID, COLUMN_NOTIFID, COLUMN_POST_ID, COLUMN_ACTION, COLUMN_WALL, COLUMN_USER
    };

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ERPWall getWall() {
        return wall;
    }

    public void setWall(ERPWall wall) {
        this.wall = wall;
    }

    public ERPUser[] getUser() {
        return user;
    }

    public void setUser(ERPUser[] user) {
        this.user = user;
    }

    public ContentValues getCV(){
        Gson gson = new Gson();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ACTION, action);
        cv.put(COLUMN_USER, gson.toJson(user));
        cv.put(COLUMN_NOTIFID, _id);
        cv.put(COLUMN_POST_ID, postId);
        cv.put(COLUMN_WALL, gson.toJson(wall));
        return cv;
    }
    public static void saveNotifications(Context context, JSONArray jsonArray) {
        JSONObject jNotification, jPost;
        ERPNotification erpNotification;
        Gson gson = new Gson();
        String action;
        DatabaseHelper data = new DatabaseHelper(context);
        for (int i = 0; i < jsonArray.length(); i++){
            try {
                jNotification = jsonArray.getJSONObject(i);
                jPost = jNotification.getJSONObject("post");
                action = jNotification.getString("action");

                erpNotification = new ERPNotification();
                erpNotification.set_id(jNotification.getString("_id"));
                erpNotification.setPostId(jPost.getString("_id"));
                erpNotification.setAction(action);
                erpNotification.setWall(
                        gson.fromJson(jPost.getJSONObject("wall").toString(), ERPWall.class) );
                if (action.equals("post")){
                    ERPUser[] erpUsers = {gson.fromJson(
                            jNotification.getJSONObject("postedBy").toString(), ERPUser.class)};
                    erpNotification.setUser(erpUsers);
                } else if (action.equals("comment")){
                    erpNotification.setUser(gson.fromJson(
                            jNotification.getJSONArray("commentedBy").toString(), ERPUser[].class) );
                }
                data.addNotification(erpNotification.getCV());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
//    KEY_ROWID, COLUMN_NOTIFID, COLUMN_POST_ID, COLUMN_ACTION, COLUMN_WALL, COLUMN_USER

    public static List<ERPNotification> getArrayList(Cursor c) {
        List<ERPNotification> list = new ArrayList<>();
        ERPNotification notif;
        Gson gson = new Gson();
        while (c.moveToNext()){
            notif = new ERPNotification();
            notif.set_id(c.getString(1));
            notif.setPostId(c.getString(2));
            notif.setAction(c.getString(3));
            notif.setWall(gson.fromJson(c.getString(4), ERPWall.class));
            notif.setUser(gson.fromJson(c.getString(5), ERPUser[].class));
            list.add(notif);
        }
        return list;
    }
}

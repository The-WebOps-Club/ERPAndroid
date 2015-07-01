package org.saarang.erp.Objects;

import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Helper.DatabaseHelper;

/**
 * Created by Ahammad on 30/06/15.
 */
public class ERPPost {


    String postId, info, title, createdOn;
    ERPWall wall;


    boolean ifAcknowledged;

    public static String TABLE_NAME = "ERPNewsFeed";

    private static String KEY_ROWID = "_id";
    public static String COLUMN_LOGIC = "logic";
    public static String COLUMN_POST_ID = "postId";
    public static String COLUMN_INFO = "info";
    public static String COLUMN_WALL = "wall";
    public static String COLUMN_TITLE = "title";
    public static String COLUMN_DATE = "date";
    public static String COLUMN_ACKNOWLEDGE = "acknowledge";
    public static String COLUMN_IF_ACKNOWLEDGED = "ifAcknowleged";
    public static String COLUMN_COMMENTS = "comments";



    public static String ERPNEWSFEED_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            KEY_ROWID + " INTEGER " + " PRIMARY KEY , " +
            COLUMN_LOGIC + " INTEGER " + " NOT NULL , " +
            COLUMN_POST_ID + " TEXT NOT NULL UNIQUE , " +
            COLUMN_INFO + " TEXT NOT NULL , " +
            COLUMN_WALL + " TEXT  , " +
            COLUMN_TITLE + " TEXT  , " +
            COLUMN_DATE + " TEXT NOT NULL , " +
            COLUMN_ACKNOWLEDGE + " TEXT  , " +
            COLUMN_IF_ACKNOWLEDGED + " NUMBER  , " +
            COLUMN_COMMENTS + " TEXT   " +
            " );";

    public static String[] columns = {KEY_ROWID, COLUMN_LOGIC, COLUMN_POST_ID, COLUMN_INFO, COLUMN_WALL, COLUMN_TITLE, COLUMN_DATE,
            COLUMN_ACKNOWLEDGE,COLUMN_COMMENTS, COLUMN_IF_ACKNOWLEDGED};

    public ERPPost(String postId, String info, String title, String createdOn, ERPWall wall, boolean ifAcknowledged) {
        this.postId = postId;
        this.info = info;
        this.title = title;
        this.createdOn = createdOn;
        this.wall = wall;
        this.ifAcknowledged = ifAcknowledged;
    }

    public ERPPost(){

    }

    public String getPostId() {

        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public ERPWall getWall() {
        return wall;
    }

    public void setWall(ERPWall wall) {
        this.wall = wall;
    }

    public boolean isAcknowledged() {
        return ifAcknowledged;
    }

    public void setAcknowledged(boolean ifAcknowledged) {
        this.ifAcknowledged = ifAcknowledged;
    }

    public long SavePost(Context context){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POST_ID, postId);
        cv.put(COLUMN_WALL, new Gson().toJson(wall) );
        cv.put(COLUMN_INFO, info);
        cv.put(COLUMN_LOGIC, 1);
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE, createdOn);
        cv.put(COLUMN_ACKNOWLEDGE, " ");
        cv.put(COLUMN_ACKNOWLEDGE, 0);
        cv.put(COLUMN_COMMENTS, "");
        DatabaseHelper data = new DatabaseHelper(context);
        return data.addNewsFeed(cv);
    }


    public static void SavePosts(Context context, JSONArray jsonArray) {
        Gson gson = new Gson();
        try {
            for (int i = 0; i< jsonArray.length(); i++){
                JSONObject post = jsonArray.getJSONObject(i);
                ERPWall wall = gson.fromJson(post.getJSONObject("wall").toString(), ERPWall.class);
                ERPPost erpPost = new ERPPost(post.getString("_id"), post.getString("info"), post.getString("title"), post.getString("createdOn"),
                        wall, false);
                erpPost.SavePost(context);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
}

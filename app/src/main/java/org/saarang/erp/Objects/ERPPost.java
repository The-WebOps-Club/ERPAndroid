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
 * Created by Ahammad on 30/06/15.
 */
public class ERPPost {


    String postId, info, title, createdOn, comments, acknowledge, updatedOn, createdBy;
    ERPWall wall;

    Gson gson = new Gson();

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
    public static String COLUMN_UPDATED = "updated";
    public static String COLUMN_CREATED_BY = "createdBy";

    public static String ERPNEWSFEED_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            KEY_ROWID + " INTEGER " + " PRIMARY KEY , " +
            COLUMN_LOGIC + " INTEGER " + " NOT NULL , " +
            COLUMN_POST_ID + " TEXT NOT NULL UNIQUE ON CONFLICT REPLACE , " +
            COLUMN_INFO + " TEXT NOT NULL , " +
            COLUMN_WALL + " TEXT  , " +
            COLUMN_TITLE + " TEXT  , " +
            COLUMN_DATE + " TEXT NOT NULL , " +
            COLUMN_ACKNOWLEDGE + " TEXT  , " +
            COLUMN_IF_ACKNOWLEDGED + " NUMBER  , " +
            COLUMN_COMMENTS + " TEXT , " +
            COLUMN_UPDATED + " TEXT ,  " +
            COLUMN_CREATED_BY + " TEXT   " +
            " );";

    public static String[] columns = {KEY_ROWID, COLUMN_LOGIC, COLUMN_POST_ID, COLUMN_INFO, COLUMN_WALL, COLUMN_TITLE, COLUMN_DATE,
            COLUMN_ACKNOWLEDGE,COLUMN_COMMENTS, COLUMN_IF_ACKNOWLEDGED, COLUMN_UPDATED, COLUMN_CREATED_BY};


    public ERPPost(String postId, String info, String title, String createdOn, ERPWall wall, boolean ifAcknowledged,
        String comments, String acknowledge, String updatedOn, String createdBy) {
        this.postId = postId;
        this.info = info;
        this.title = title;
        this.createdOn = createdOn;
        this.wall = wall;
        this.ifAcknowledged = ifAcknowledged;
        this.comments = comments;
        this.acknowledge = acknowledge;
        this.updatedOn = updatedOn;
        this.createdBy = createdBy;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAcknowledge() {
        return acknowledge;
    }

    public void setAcknowledge(String acknowledge) {
        this.acknowledge = acknowledge;
    }

    public void setAcknowledged(boolean ifAcknowledged) {
        this.ifAcknowledged = ifAcknowledged;
    }

    public ERPUser getPostedBy(){
        ERPUser user = gson.fromJson(createdBy, ERPUser.class);
        return user;
    }

    public long SavePost(Context context){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_POST_ID, postId);
        cv.put(COLUMN_WALL, new Gson().toJson(wall) );
        cv.put(COLUMN_INFO, info);
        cv.put(COLUMN_LOGIC, 1);
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DATE, createdOn);
        cv.put(COLUMN_ACKNOWLEDGE, acknowledge);
        cv.put(COLUMN_IF_ACKNOWLEDGED, 0);
        cv.put(COLUMN_COMMENTS, comments);
        cv.put(COLUMN_UPDATED, updatedOn);
        cv.put(COLUMN_CREATED_BY, createdBy);
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
                        wall, false, post.getJSONArray("comments").toString(), post.getJSONArray("acknowledged").toString(),
                        post.getString("updatedOn"), post.getString("createdBy"));
                erpPost.SavePost(context);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public static List<ERPPost> getArrayList(Cursor c){
        List<ERPPost> arrayList = new ArrayList<>();
        Gson gson = new Gson();
        while ( c.moveToNext() ){
            ERPPost post = new ERPPost(c.getString(2), c.getString(3), c.getString(5), c.getString(6),
                    gson.fromJson(c.getString(4), ERPWall.class), (c.getInt(9) > 0), c.getString(8), c.getString(7),
                    c.getString(10), c.getString(11) );
            arrayList.add(post);
        }
        return arrayList;
    }
}

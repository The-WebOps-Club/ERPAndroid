package org.saarang.erp.Objects;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahammad on 28/06/15.
 */
public class ERPComment {
    String info;
    String _id;
    Gson gson = new Gson();

    String userId;
    String createdOn;
    ERPUser createdBy;

    public ERPComment(String _id, String info, String createdOn, String createdBy) {
        this.info = info;
        this._id = _id;
        this.createdOn= createdOn;
        this.createdBy = gson.fromJson(createdBy, ERPUser.class);
    }

    public ERPComment(String _id, String info, String createdOn, ERPUser createdBy) {
        this.info = info;
        this._id = _id;
        this.createdOn= createdOn;
        this.createdBy = createdBy;
    }

    public ERPComment() {

    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public ERPUser getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(ERPUser createdBy) {
        this.createdBy = createdBy;
    }


    public static List<ERPComment> getCommentsFromString(String commentString){
        List<ERPComment> comments = new ArrayList<>();
        Gson gson = new Gson();
        try {
            JSONObject json = new JSONObject("{ \"comments\": " + commentString + " }");
            JSONArray array = json.getJSONArray("comments");
            for (int i =0; i<array.length(); i++){
                JSONObject commentJSON =   array.getJSONObject(i);
                ERPComment comment = new ERPComment(commentJSON.getString("_id"), commentJSON.getString("info"),
                        commentJSON.getString("createdOn"), commentJSON.getString("createdBy"));
                comments.add(comment);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comments;
    }
}

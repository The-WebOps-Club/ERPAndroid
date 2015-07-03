package org.saarang.erp.Objects;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajmal on 03-07-2015.
 */
public class ERPAcknowledged {

    ERPUser createdBy;
    Gson gson = new Gson();

    //constructor
    public ERPAcknowledged(String createdBy){
        this.createdBy = gson.fromJson(createdBy,ERPUser.class);
    }

    public ERPUser getCreatedBy(){
        return createdBy;
    }

    public static List<ERPAcknowledged> getAcknowledgedFromString(String acknoString)
    {
        List<ERPAcknowledged> acknowledgedList = new ArrayList<>();
        try{
            JSONObject json = new JSONObject("{\"acknowledged\":" + acknoString + "}");
            JSONArray array =json.getJSONArray("acknowledged");
            for(int i=0; i<array.length(); i++){
                JSONObject acknoJSON = array.getJSONObject(i);
                ERPAcknowledged ackno = new ERPAcknowledged(acknoJSON.toString());
                acknowledgedList.add(ackno);
            }
        }
        catch(JSONException e){
            e.printStackTrace();
        }
        return acknowledgedList;

    }


}
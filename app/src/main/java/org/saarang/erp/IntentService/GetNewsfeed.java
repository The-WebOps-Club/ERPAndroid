package org.saarang.erp.IntentService;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPUser;
import org.saarang.erp.Objects.ERPWall;
import org.saarang.erp.Utils.SPUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.GetRequest;

/**
 * Created by Ahammad on 30/06/15.
 */
public class GetNewsfeed extends IntentService {

    private static String LOG_TAG = "GetNewsfeed";

    JSONObject json;
    int pageNumber = 1;
    int status = 200;
    JSONArray jsonArray;
    String oldestPost;

    public GetNewsfeed() {
        super("GetNewsfeed");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        while (status == 200){
            json = GetRequest.execute(URLConstants.URL_NEWSFEED_PAGE+ pageNumber , ERPUser.getERPUserToken(this));
            Log.d(LOG_TAG, json.toString());
            Gson gson = new Gson();
            try {

                status = json.getInt("status");
                jsonArray = json.getJSONObject("data").getJSONArray("response");

                // Get the time of latest post and save it to SP
                if (pageNumber == 1)
                    SPUtils.setLatestPostDate(this, json.getJSONObject("data").getJSONArray("response").getJSONObject(0).getString("updatedOn"));

                for (int i = 0; i< jsonArray.length(); i++){
                    JSONObject post = jsonArray.getJSONObject(i);
                    ERPWall wall = gson.fromJson(post.getJSONObject("wall").toString(), ERPWall.class);
                    ERPPost erpPost = new ERPPost(post.getString("_id"), post.getString("info"), post.getString("title"), post.getString("createdOn"), wall);
                    erpPost.SavePost(this);
                    oldestPost = post.getString("updatedOn");
                }
                pageNumber++;

            } catch (JSONException e) {
                status = 300;
            }
        }

        // Save time of oldest post to SP
        SPUtils.setOldestPostDate(this, oldestPost);

        // Mark that newsfeed is downloaded once
        SPUtils.setNewsFeedDownloadedOnce(this);

    }

}

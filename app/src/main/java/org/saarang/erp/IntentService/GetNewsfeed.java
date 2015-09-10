package org.saarang.erp.IntentService;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Objects.ERPNotification;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPProfile;
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

    public GetNewsfeed() {
        super("GetNewsfeed");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(LOG_TAG, " Started ");

        while (status == 200){
            json = GetRequest.execute(URLConstants.URL_NEWSFEED_PAGE+ pageNumber , ERPProfile.getERPUserToken(this));
            Log.d(LOG_TAG, json.toString());
            try{
                // Getting notifications
                json = GetRequest.execute(URLConstants.URL_NOTIFICATIONS_FETCH,
                        ERPProfile.getERPUserToken(this));
                Log.d(LOG_TAG, "notifications: "+json.toString());

                // Get status of the response
                status = json.getInt("status");
                Log.d(LOG_TAG, "notifs response: "+status);

                // Extract posts from response
                jsonArray = json.getJSONObject("data").getJSONArray("response");
                Log.d(LOG_TAG, "comments:" + jsonArray.toString());
                ERPNotification.saveNotifications(this, jsonArray);


                Intent notificationsDownloadComplete = new Intent("notifications_download_complete");
                LocalBroadcastManager.getInstance(this).sendBroadcast(notificationsDownloadComplete);


            } catch (JSONException e){
                e.printStackTrace();
            }

            try {
                // Make request
                json = GetRequest.execute(URLConstants.URL_NEWSFEED_PAGE + pageNumber, ERPProfile.getERPUserToken(this));
                Log.d(LOG_TAG,"get news feed "+ json.toString());

                // Get status of the response
                status = json.getInt("status");

                // Extract posts from response
                jsonArray = json.getJSONObject("data").getJSONArray("response");

                // Save posts to DB
                ERPPost.SavePosts(this, jsonArray);

                // Get the time of latest post and save it to SP
                if (pageNumber == 1)
                    SPUtils.setLatestPostDate(this, json.getJSONObject("data").getJSONArray("response").getJSONObject(0).getString("updatedOn"));

                // Save time of oldest post to SP
                SPUtils.setOldestPostDate(this, json.getJSONObject("data").getJSONArray("response").getJSONObject(jsonArray.length() - 1).getString("updatedOn"));

                // Go to next page
                pageNumber++;

                // Set time of last update
                SPUtils.setLastUpdateTime(this, System.currentTimeMillis());

            } catch (JSONException e) {
                status = 300;
            }
        }


//
//        // Getting users
//        try{
//            json = GetRequest.execute(URLConstants.URL_PEOPLE_FETCH_ALL,
//                    ERPProfile.getERPUserToken(this));
//
//            // Get status of the response
//            status = json.getInt("status");
//
//            // Extract posts from response
//            jsonArray = json.getJSONObject("data").getJSONArray("response");
//            Log.d(LOG_TAG, "Users " +  jsonArray.toString());
//
//            if (status/100 == 2){
//                JSONObject jUser;
//                ERPUser user;
//                Gson gson = new Gson();
//                for (int i = 0; i < jsonArray.length(); i++){
//                    jUser = jsonArray.getJSONObject(i);
//                    user = gson.fromJson(jUser.toString(), ERPUser.class);
//                    user.saveUser(this);
//                }
//            }
//        } catch (JSONException e){
//
//        }

        // Mark that news feed is downloaded once
        SPUtils.setNewsFeedDownloadedOnce(this);

    }

}

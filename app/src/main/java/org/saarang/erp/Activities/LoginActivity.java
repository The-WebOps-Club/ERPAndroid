package org.saarang.erp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.R;
import org.saarang.saarangsdk.Network.GetRequest;


public class LoginActivity extends Activity {

    Button bLogin;
    private static String LOG_TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);


        //Setting the background pic

        new GetCoverPic().execute();





        /**
         * Login Button
         */
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * AsyncTask for getting the cover pic of the user.
     */
    private class GetCoverPic extends AsyncTask<Void, Void, Void> {

        boolean ifContinue = true;

        @Override
        protected Void doInBackground(Void... aVoid) {
            String urlString = "https://graph.facebook.com/me?fields=cover&access_token=34baa";
//            Log.d(LOG_TAG, urlString);
            try {
                JSONObject responseJSON = GetRequest.execute(urlString, null);
                if (responseJSON != null) {
                    Log.d(LOG_TAG, responseJSON.toString());

                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "Get cover pic failed");
            }
            if (isCancelled()) ifContinue = false;
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }



}

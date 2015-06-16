package org.saarang.erp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONObject;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;


public class LoginActivity extends Activity {

    Button bLogin;
    private static String LOG_TAG = "LoginActivity";
    LinearLayout llMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);

        llMain = (LinearLayout) findViewById(R.id.llMain);

        /**
         * Login Butto
         */
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking for connection
                if (Connectivity.isConnected()){
                    new Login().execute();
                } else {
                    UIUtils.showSnackBar(v, getResources().getString(R.string.error_connection));
                }
            }
        });
    }

    /**
     * AsyncTask for Logging in .
     */
    private class Login extends AsyncTask<Void, Void, Void> {

        ArrayList<PostParam> params = new ArrayList<PostParam>();
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... aVoid) {

            String urlString = URLConstants.SERVER + URLConstants.URL_LOGIN;

            //Adding Parameters
            params.add(new PostParam("email", "deepakpadamata@gmail.com"));
            params.add(new PostParam("password", "saarang"));
            params.add(new PostParam("deviceId", "password"));

            //Making request
            JSONObject responseJSON = PostRequest.execute(urlString, params, null);
            if (responseJSON == null) {
                return null;
            }


            Log.d(LOG_TAG, responseJSON.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
        }
    }



}

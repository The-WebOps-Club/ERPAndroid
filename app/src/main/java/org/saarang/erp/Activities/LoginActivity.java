package org.saarang.erp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Objects.ERPUser;
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
    Login logintask = null;
    ProgressDialog pDialog;
    LinearLayout layout;
    EditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);


        layout = (LinearLayout) findViewById(R.id.llMain);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        /**
         * Login Button
         */
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking for connection
                if (Connectivity.isConnected()){
                    logintask = new Login();
                    logintask.execute(etEmail.getText().toString(), etPassword.getText().toString());
                } else {
                    UIUtils.showSnackBar(v, getResources().getString(R.string.error_connection));
                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        logintask.cancel(true);

    }

    /**
     * AsyncTask for Logging in .
     */
    private class Login extends AsyncTask<String, Void, Void> {

        ArrayList<PostParam> params = new ArrayList<>();
        int status = 400;

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
        protected Void doInBackground(String... param) {

            String urlString = URLConstants.SERVER + URLConstants.URL_LOGIN;

            //Adding Parameters
            params.add(new PostParam("email", param[0]));
            params.add(new PostParam("password", param[1]));
            params.add(new PostParam("deviceId", "password"));

            //Making request
            JSONObject responseJSON = PostRequest.execute(urlString, params, null);
            if (responseJSON == null) {
                return null;
            }

            try {
                status = responseJSON.getInt("status");
                if (status == 200){
                    ERPUser.saveUser(LoginActivity.this, responseJSON);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.d(LOG_TAG, responseJSON.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
            switch (status){
                case 200:
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    break;
                case 401:
                    UIUtils.showSnackBar(layout, "Invalid credentials");
                    break;
                default:
                    UIUtils.showSnackBar(layout, "There was an error connecting to our server. Please try again");
                    break;
            }
        }
    }



}

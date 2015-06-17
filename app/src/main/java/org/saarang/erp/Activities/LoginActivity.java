package org.saarang.erp.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Objects.ERPUser;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;
import org.saarang.saarangsdk.Utils.StringValidator;

import java.util.ArrayList;

import static java.lang.Thread.sleep;


public class LoginActivity extends Activity {

    Button bLogin;
    private static String LOG_TAG = "LoginActivity";
    EditText etEmail;
    EditText etPassword;
    String email;
    String password;
    TextInputLayout tilEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);

        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);

        //Getting EditText from xml

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPassword = (EditText)findViewById(R.id.etPassword);

        /**
         * Login Button
         */

        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting String from the EditText fields

                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                //Validating email id

                if (StringValidator.isEmailValid(email)) {
                    Log.d(LOG_TAG, "valid email");
                    tilEmail.setError(null);
                    
                    //Checking for connection

                    if (Connectivity.isConnected()) {
                        new Login().execute();
                    } else {
                        UIUtils.showSnackBar(v, getResources().getString(R.string.error_connection));
                    }

                } else {

                    //Displaying Error Message

                    Log.d(LOG_TAG, "invalid email id");
                    tilEmail.setError("Invalid Email Id entered");
                }
            }
        });

    }

    /**
     * AsyncTask for Logging in .
     */
    private class Login extends AsyncTask<Void, Void, Void> {

        ArrayList<PostParam> params = new ArrayList<>();
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
            params.add(new PostParam("email", email));
            params.add(new PostParam("password", password));
            params.add(new PostParam("deviceId", "password"));

            //Making request
            JSONObject responseJSON = PostRequest.execute(urlString, params, null);
            if (responseJSON == null) {
                return null;
            }

            try {
                Log.d(LOG_TAG, responseJSON.getJSONObject("data").toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ERPUser.saveUser(LoginActivity.this, responseJSON);


            Log.d(LOG_TAG, responseJSON.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            pDialog.dismiss();
        }
    }



}

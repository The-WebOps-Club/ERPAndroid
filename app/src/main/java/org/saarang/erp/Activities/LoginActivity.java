package org.saarang.erp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Objects.ERPUser;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.erp.Utils.UserState;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;
import org.saarang.saarangsdk.Utils.StringValidator;

import java.util.ArrayList;


public class LoginActivity extends Activity {

    Button bLogin;
    private static String LOG_TAG = "LoginActivity";
    Login logintask = null;
    ResetPassword resetpassword;
    ProgressDialog pDialog;
    LinearLayout layout;
    EditText etEmail, etPassword;
    TextInputLayout tilEmail;
    TextView tvForgotPassword;
    String email = "";
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);

        UserState.setLastActivity(LoginActivity.this, 1);


        layout = (LinearLayout) findViewById(R.id.llMain);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        tvForgotPassword = (TextView) findViewById(R.id.tvForgotPassword);

        /**
         * Login Button
         */
        bLogin = (Button) findViewById(R.id.bLogin);
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting text from EditText
                email = etEmail.getText().toString();
                password = etPassword.getText().toString();

                //Validating email id format
                if (StringValidator.isEmailValid(email)) {
                    tilEmail.setError(null);

                    //Checking for connection

                    if (Connectivity.isConnected()) {
                        logintask = new Login();
                        logintask.execute(etEmail.getText().toString(), etPassword.getText().toString());
                    } else {
                        Log.d(LOG_TAG, "no net");
                        UIUtils.showSnackBar(v, getResources().getString(R.string.error_connection));
                    }
                } else {
                    tilEmail.setError("Invalid email id");
                }
            }
        });

        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                //Building the dialog box

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Confirm reset password");
                builder.setMessage("A mail will be sent to you to reset" +
                        " the password when you click OK.");
                //Creating ok button with listener
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("Alert", " Ok");

                        //Getting email

                        email = etEmail.getText().toString();

                        //Validating email
                        if (StringValidator.isEmailValid(email)) {
                            tilEmail.setError(null);

                            //Checking for connection

                            if (Connectivity.isConnected()) {

                                //Asking server to send email to reset password

                                resetpassword = new ResetPassword();
                                resetpassword.execute(etEmail.getText().toString());
                            } else {
                                Log.d(LOG_TAG, "no internet");
                                UIUtils.showSnackBar(v, getResources().getString(R.string.error_connection));
                            }
                        } else {
                            tilEmail.setError("Invalid email id");
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(logintask != null) logintask.cancel(true);

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
                    Intent intent = new Intent(LoginActivity.this, ProfilePictureActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 401:
                    UIUtils.showSnackBar(layout, "Invalid credentials, please try again");
                    break;
                default:
                    UIUtils.showSnackBar(layout, "There was an error connecting to our server. Please try again");
                    break;
            }
        }
    }

    /**
     * AsyncTask for Resetting password .
     */
    private class ResetPassword extends AsyncTask<String, Void, Void> {

        ArrayList<PostParam> params = new ArrayList<>();
        int status = 400;

        @Override
        protected Void doInBackground(String... param) {

            String urlString = URLConstants.SERVER + URLConstants.URL_FORGOT_PASSWORD;

            params.add(new PostParam("email", param[0]));

            //Making request

            JSONObject responseJSON = PostRequest.execute(urlString, params, null);
            if (responseJSON == null) {
                return null;
            }

            try {
                status = responseJSON.getInt("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Log.d(LOG_TAG, responseJSON.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            switch (status) {
                case 200:
                    UIUtils.showSnackBar(layout, "An email has been sent by us, please check your inbox and reset your password");
                    break;
                case 401:
                    UIUtils.showSnackBar(layout, "Invalid credentials");
                    break;
                case 404:
                    UIUtils.showSnackBar(layout, "You have not registered yet, please sign up " +
                            "and try again");
                    break;
                default:
                    UIUtils.showSnackBar(layout, "There was an error connecting to our server. Please try again");
                    break;
            }
        }
    }

 }

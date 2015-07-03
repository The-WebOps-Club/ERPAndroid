package org.saarang.erp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
//importing Google Gson


import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.erp.Utils.UserState;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;

//importing Google Gson

public class UpdateProfileActivity extends AppCompatActivity {

    private static String LOG_TAG = "Update Profile Activity";
     EditText etName,etSummerLocation,etPhone,etAlternateNumber,etRoomNumber;
     LinearLayout layout;
     ProgressDialog pDialog;
     SendUpdate update=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_update_profile);

        UserState.setLastActivity(UpdateProfileActivity.this,3);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setting up EditText s and linearLayout

        etName=(EditText)findViewById(R.id.etName);
        etSummerLocation=(EditText)findViewById(R.id.etSummerLocation);
        etPhone=(EditText)findViewById(R.id.etPhone);
        etAlternateNumber=(EditText)findViewById(R.id.etAlternateNumber);
        etRoomNumber=(EditText)findViewById(R.id.etRoomNumber);


        layout=(LinearLayout)findViewById(R.id.llUpdateProfile);

        //Set the stored text into fields

        etName.setText(ERPProfile.getERPUserName(UpdateProfileActivity.this));
        etSummerLocation.setText(ERPProfile.getERPUserSummerLocation(UpdateProfileActivity.this));
        etPhone.setText(ERPProfile.getERPUserPhoneNumber(UpdateProfileActivity.this));
        etAlternateNumber.setText(ERPProfile.getERPUserAlternateNumber(UpdateProfileActivity.this));
        etRoomNumber.setText(ERPProfile.getERPUserRoomNumber(UpdateProfileActivity.this));


        //Setting up Hostels spinner

        final Spinner spHostels=(Spinner)findViewById(R.id.spHostels);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.hostels,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spHostels.setAdapter(adapter);


        Button bUpdate = (Button)findViewById(R.id.bUpdate);

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sName=etName.getText().toString();
                String sSummerLocation=etSummerLocation.getText().toString();
                String sPhone=etPhone.getText().toString();
                String sAlternateNumber=etAlternateNumber.getText().toString();
                String sRoomNumber=etRoomNumber.getText().toString();
                String sHostel=spHostels.getSelectedItem().toString();


               boolean isValidated =  validate(sName,sSummerLocation,sPhone,sAlternateNumber,sRoomNumber);
                if(isValidated){
                    //Checking for connection
                    if (Connectivity.isConnected()){

                        update = new SendUpdate();
                        update.execute(sName,sSummerLocation,sPhone,sAlternateNumber,sRoomNumber,sHostel);
                    } else {
                        UIUtils.showSnackBar(layout, getResources().getString(R.string.error_connection));
                    }
                }
            }
        });


    }
    public boolean validate(String sName,String sSummerLocation,String sPhone,String sAlternateNumber,String sRoomNumber){


        //Validating if entry is null
        if(sName.length() *  sSummerLocation.length() * sPhone.length() * sAlternateNumber.length()*sRoomNumber.length() ==0)
        {
            UIUtils.showSnackBar(layout, "Invalid Inputs");
            return false;
        }
        else if (sPhone.length()!=10)
        {
            UIUtils.showSnackBar(layout, "Invalid Phone Number");
            return false;
        }
        else if(sAlternateNumber.length()!=10){
            UIUtils.showSnackBar(layout, "Invalid Alternate Mobile Number");
            return false;
        }
        else {
            return true;
        }
    }

    private class SendUpdate extends AsyncTask<String, Void,Void>{
        ArrayList<PostParam> putData= new ArrayList<>();
        String urlString = URLConstants.SERVER + URLConstants.URL_UPDATE1+"/"+ ERPProfile.getERPUserId(UpdateProfileActivity.this)+"/"+URLConstants.URL_UPDATE2;
        int status = 400;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(UpdateProfileActivity.this);
                pDialog.setMessage("Updating ...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            }


          @Override
           protected Void doInBackground(String... param) {

               //Adding Parameters

               putData.add(new PostParam("name", param[0]));
               putData.add(new PostParam("summerLocation", param[1]));
               putData.add(new PostParam("phoneNumber", param[2]));
               putData.add(new PostParam("alternateNumber", param[3]));
               putData.add(new PostParam("roomNumber", param[4]));
               putData.add(new PostParam("profilePic", ERPProfile.getUserProfilePicId(UpdateProfileActivity.this)));
              putData.add(new PostParam("hostel",param[5]));

               //Making request
               JSONObject responseJSON = PostRequest.execute(urlString, putData, ERPProfile.getERPUserToken(UpdateProfileActivity.this));
               if (responseJSON == null) {
                   return null;
               }

               try {
                   status = responseJSON.getInt("status");
                   if (status == 200){
                       ERPProfile.saveUser(UpdateProfileActivity.this, responseJSON);
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
                    UserState.setLastActivity(UpdateProfileActivity.this,4);
                    Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
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

    public void startMainActivity(){
        Intent intent = new Intent(UpdateProfileActivity.this, MainActivity.class);
        startActivity(intent);
    }

}

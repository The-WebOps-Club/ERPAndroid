package org.saarang.erp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.IntentService.GetNewsfeed;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;
import org.saarang.erp.Utils.AppConstants;
import org.saarang.erp.Utils.SPUtils;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.erp.Utils.UserState;
import org.saarang.saarangsdk.Helpers.SaarangImageHelper;
import org.saarang.saarangsdk.Network.ImageUploader;
import org.saarang.saarangsdk.Objects.PostParam;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ProfilePictureActivity extends AppCompatActivity implements View.OnClickListener{

    Bitmap bitmap;
    ImageView ivProfilePic;
    private static final int REQUEST_CODE_CROP_IMAGE = 101;
    private static final String LOG_TAG = "ProfilePictureActivity";
    Uri destination;
    TextView tvChangeImage, tvContinue;
    ProgressDialog pDialog;
    UpdateProfile updateProfile;
    String filePath;
    boolean imageLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile_picture);

        // Check if News feed is downloaded once
        if ( !SPUtils.ifNewsFeedDownloaded(this) ) {

            // Start service to download
            Intent intent = new Intent(this, GetNewsfeed.class);
            startService(intent);

        }

        UserState.setLastActivity(ProfilePictureActivity.this,2);

        // Buttons and onclick listeners
        ivProfilePic = (ImageView) findViewById(R.id.ivProfilePic);
        tvChangeImage = (TextView) findViewById(R.id.tvChangeImage);
        tvContinue = (TextView) findViewById(R.id.tvContinue);
        tvChangeImage.setOnClickListener(this);
        ivProfilePic.setOnClickListener(this);

        // Check for already set profile pic
        final String profilePicUrl = URLConstants.SERVER + "api/users/" + ERPProfile.getERPUserId(this) + "/profilePic";
        Picasso.with(this)
                .load(profilePicUrl)
                .placeholder(R.drawable.placeholder_profile)
                .into(ivProfilePic, new Callback() {

                    @Override
                    public void onSuccess() {
                        tvContinue.setTextColor(ProfilePictureActivity.this.getResources().getColor(R.color.white));
                        tvContinue.setOnClickListener(ProfilePictureActivity.this);
                        imageLoaded = true;
                    }

                    @Override
                    public void onError() {
                    }
                });

        // Check if profile picture is already set
        if (ERPProfile.getUserProfilePic(this) != ""){
            File imgFile = new  File(ERPProfile.getUserProfilePic(this));
            if(imgFile.exists()){
                Bitmap profilePic = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ivProfilePic.setImageBitmap(profilePic);
            }
        }

    }

    private void beginCrop(Uri source) {
//        File file = new File(getCacheDir(), "cropped");
        File file = new File(Environment.getExternalStorageDirectory(), "cropped");
        if (file.exists ()) file.delete ();
        destination = Uri.fromFile(file);
        Crop.of(source, destination).asSquare().start(this);
        tvContinue.setOnClickListener(this);
        Log.d(LOG_TAG, "Image destinaton" + destination);
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvChangeImage:
                Crop.pickImage(ProfilePictureActivity.this);
                break;
            case R.id.ivProfilePic:
                Crop.pickImage(ProfilePictureActivity.this);
                break;
            case R.id.tvContinue:
                if (imageLoaded){
                    Intent intent = new Intent(ProfilePictureActivity.this, UpdateProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
                updateProfile = new UpdateProfile();
                updateProfile.execute();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            //doSomethingWithCroppedImage(outputUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), destination);
                ivProfilePic.setImageBitmap(bitmap);
                filePath = SaarangImageHelper.compressSaveImage(ProfilePictureActivity.this, bitmap,
                        AppConstants.PROFILE_PICTURE);
                tvContinue.setTextColor(ProfilePictureActivity.this.getResources().getColor(R.color.white));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile_picture, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class UpdateProfile extends AsyncTask<String, Void, Void> {

        ArrayList<PostParam> params = new ArrayList<>();
        int status = 400;
        String profilePicPath, fileId;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ProfilePictureActivity.this);
            pDialog.setMessage("Uploading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... param) {

            String link = URLConstants.SERVER + URLConstants.URL_UPLOAD;
            profilePicPath = getCacheDir().toString() +"/saved_images/" + AppConstants.PROFILE_PICTURE + ".jpg";
//            profilePicPath = "/data/data/org.saarang.erp/cache/saved_images/" + AppConstants.PROFILE_PICTURE + ".jpg";
            JSONObject json = ImageUploader.execute( link, filePath, ERPProfile.getERPUserToken(ProfilePictureActivity.this));
            try {
                status = json.getInt("status");
                fileId = json.getJSONObject("data").getString("fileId");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            Log.d(LOG_TAG, json.toString());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
            switch (status){
                case 200:
                    ERPProfile.setUserProfilePic(ProfilePictureActivity.this, profilePicPath);
                    ERPProfile.setUserProfilePicId(ProfilePictureActivity.this, fileId);
                    Intent intent = new Intent(ProfilePictureActivity.this, UpdateProfileActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    UIUtils.showSnackBar(findViewById(android.R.id.content), "Connection error" );
                    break;
            }
        }
    }

}

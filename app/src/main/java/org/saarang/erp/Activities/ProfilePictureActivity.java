package org.saarang.erp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.soundcloud.android.crop.Crop;

import org.saarang.erp.Helpers.SaarangImageHelper;
import org.saarang.erp.R;

import java.io.File;
import java.io.IOException;

public class ProfilePictureActivity extends ActionBarActivity implements View.OnClickListener{

    Bitmap bitmap;
    ImageView ivProfilePic;
    private static final int REQUEST_CODE_CROP_IMAGE = 101;
    private static final String LOG_TAG = "ProfilePictureActivity";
    Uri destination;
    TextView tvChangeImage, tvContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);
        ivProfilePic = (ImageView) findViewById(R.id.ivProfilePic);
        tvChangeImage = (TextView) findViewById(R.id.tvChangeImage);
        tvContinue = (TextView) findViewById(R.id.tvContinue);
        tvChangeImage.setOnClickListener(this);
        tvContinue.setOnClickListener(this);
        ivProfilePic.setOnClickListener(this);

    }

    private void beginCrop(Uri source) {
        File file = new File(getCacheDir(), "cropped");
       // if (file.exists ()) file.delete ();
        destination = Uri.fromFile(file);
        Crop.of(source, destination).asSquare().start(this);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),destination);
            ivProfilePic.setImageBitmap(bitmap);
            SaarangImageHelper.compressSaveImage(bitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            //doSomethingWithCroppedImage(outputUri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),destination);
                ivProfilePic.setImageBitmap(bitmap);
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


}

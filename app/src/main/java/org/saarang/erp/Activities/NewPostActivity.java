package org.saarang.erp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener{

    String[] departements = {"Finance", "Publicity", "Design and Media", "Marketing and Sales",
            "Mobile Operations", "Web Operations"};
    EditText etTitle, etBody;
    Button bSubmit;
    String title, body;
    CreateNewPost createNewPost;
    private static String LOG_TAG = "NewPostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_new_post);

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationIcon(R.drawable.ic_arrow_left);

        Intent intent = getIntent();

        //Add array adapter for dropdown edit text
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, departements);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.tvDeps);
        textView.setAdapter(adapter);

        etBody = (EditText) findViewById(R.id.etBody);
        etTitle = (EditText) findViewById(R.id.etTitle);

        bSubmit = (Button) findViewById(R.id.bSubmit);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bSubmit:
                title = etTitle.getText().toString();
                body = etBody.getText().toString();
                if ( title.isEmpty() || body.isEmpty() ){
                    UIUtils.showSnackBar(findViewById(android.R.id.content), "Invalid inputs");
                    return;
                }
                createNewPost = new CreateNewPost();
                createNewPost.execute(title, body);
                break;
        }
    }

    private class CreateNewPost extends AsyncTask<String, Void, Void>{

        JSONObject json;
        ArrayList<PostParam> params;
        @Override
        protected Void doInBackground(String... param) {
            json = PostRequest.execute(URLConstants.URL_POST_NEW, null, ERPProfile.getERPUserToken(NewPostActivity.this));
            Log.d(LOG_TAG, json.toString());
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        if (createNewPost != null)
            createNewPost.cancel(true);
        super.onDestroy();
    }

}

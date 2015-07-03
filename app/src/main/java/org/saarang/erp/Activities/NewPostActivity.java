package org.saarang.erp.Activities;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.Objects.ERPWall;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;
import java.util.List;

public class NewPostActivity extends AppCompatActivity implements View.OnClickListener {

    int length;
    EditText etTitle, etBody;
    String[] departements;
    String id;
    ArrayList<ERPWall> wallList;
    Button bSubmit;
    String title, body, department;
    CreateNewPost createNewPost;
    AutoCompleteTextView tvDep;
    private static String LOG_TAG = "NewPostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_new_post);

        Log.d("ARRAY", ERPProfile.getUserWalls(this));
        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationIcon(R.drawable.ic_arrow_left);

        Gson gson = new Gson();
        wallList = gson.fromJson( ERPProfile.getUserWalls(this), new TypeToken<List<ERPWall>>(){}.getType());

        length = wallList.size();
        departements = new String[length];
        makeArray();

        //Add array adapter for dropdown edit text
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, departements);
        tvDep = (AutoCompleteTextView) findViewById(R.id.tvDeps);
        tvDep.setAdapter(adapter);

        tvDep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                    tvDep.showDropDown();
            }
        });

        etTitle = (EditText)findViewById(R.id.etTitle);
        etBody = (EditText)findViewById(R.id.etBody);

        bSubmit = (Button) findViewById(R.id.bSubmit);
        bSubmit.setOnClickListener(this);

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
                department = tvDep.getText().toString();
                if ( title.isEmpty() || body.isEmpty() ){
                    UIUtils.showSnackBar(findViewById(android.R.id.content), "Invalid inputs");
                    return;
                }
                createNewPost = new CreateNewPost();
                createNewPost.execute(title, body, getId(department));
                break;
        }
    }

    private class CreateNewPost extends AsyncTask<String, Void, Void> {

        JSONObject json;
        ArrayList<PostParam> params;
        @Override
        protected Void doInBackground(String... param) {
            params = new ArrayList<>();
            params.add(new PostParam( "title" , param[0]));
            params.add(new PostParam( "info" , param[1]));
            params.add(new PostParam( "destId" , param[2]));
            json = PostRequest.execute(URLConstants.URL_POST_NEW, params, ERPProfile.getERPUserToken(NewPostActivity.this));
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

    //get list of walls as a String array
    public void makeArray(){
        for (int i = 0; i<length; i++){
            departements[i] = wallList.get(i).getName();
        }
    }

    //method to get id of wall
    public String getId(String s){
        for (int i = 0; i<length; i++){
            if(wallList.get(i).getName().equalsIgnoreCase(s))
                id = wallList.get(i).getId();
        }
        return id;
    }
}

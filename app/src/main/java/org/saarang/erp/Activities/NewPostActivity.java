package org.saarang.erp.Activities;

import android.app.ProgressDialog;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.Objects.ERPWall;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
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
    String title, body, department="", departmentId="", info = null;
    CreateNewPost createNewPost;
    AutoCompleteTextView tvDep;
    private static String LOG_TAG = "NewPostActivity";
    private static NewPostListener listener;
    Intent intent;
    public static String EXTRA_WALLNAME = "wallName";
    public static String EXTRA_WALLID = "wallId";
    boolean extraAvailable = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_new_post);

        tvDep = (AutoCompleteTextView) findViewById(R.id.tvDeps);
        etTitle = (EditText)findViewById(R.id.etTitle);
        etBody = (EditText)findViewById(R.id.etBody);
        bSubmit = (Button) findViewById(R.id.bSubmit);

        intent = getIntent();
        if (intent.getExtras() != null){
            department = intent.getStringExtra(EXTRA_WALLNAME);
            departmentId = intent.getStringExtra(EXTRA_WALLID);
            extraAvailable = true;
            Log.d(LOG_TAG, department);
            Log.d(LOG_TAG, departmentId);
        }

        Gson gson = new Gson();
        wallList = gson.fromJson( ERPProfile.getUserWalls(this), new TypeToken<List<ERPWall>>(){}.getType());

        length = wallList.size();
        departements = new String[length];
        makeArray();

        if(!extraAvailable){

            //Add array adapter for dropdown edit text
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, departements);

            tvDep.setAdapter(adapter);

            tvDep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus)
                        tvDep.showDropDown();
                }
            });
        } else{
            tvDep.setText(department);
            etTitle.setFocusable(true);
            etTitle.requestFocus();
        }

        Log.d("ARRAY", ERPProfile.getUserWalls(this));
        Toolbar tb = (Toolbar)findViewById(R.id.toolbarNewPost);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationIcon(R.drawable.ic_arrow_left);

        bSubmit.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                //          close activity on back
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
                if(!extraAvailable){
                    department = tvDep.getText().toString();
                    departmentId = getId(department);
                }
                if ( title.isEmpty() || body.isEmpty() || departmentId.isEmpty() ){
                    UIUtils.showSnackBar(findViewById(android.R.id.content), "Invalid inputs");
                    return;
                } else if (!Connectivity.isConnected()){
                    UIUtils.showSnackBar(findViewById(android.R.id.content),
                            getResources().getString(R.string.error_connection));
                    return;
                }
                createNewPost = new CreateNewPost();
                createNewPost.execute(title, body, departmentId, department);
                break;
        }
    }

    private class CreateNewPost extends AsyncTask<String, Void, Void> {

        JSONObject json, jNewPost;
        ArrayList<PostParam> params;
        int status = 989;
        Gson gson = new Gson();
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(NewPostActivity.this);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... param) {
            params = new ArrayList<>();
            params.add(new PostParam( "title" , param[0]));
            params.add(new PostParam( "info" , param[1]));
            params.add(new PostParam( "destId" , param[2]));
            json = PostRequest.execute(URLConstants.URL_POST_NEW, params,
                    ERPProfile.getERPUserToken(NewPostActivity.this));
            Log.d(LOG_TAG, json.toString());
            try {
                status = json.getInt("status");
                Log.d(LOG_TAG, json.getJSONObject("data").toString());
                jNewPost = json.getJSONObject("data");
                jNewPost.put("createdBy",
                        new JSONObject(gson.toJson(ERPProfile.getUserObject(NewPostActivity.this))));
                ERPWall wall = new ERPWall(param[2], param[3]);
                ERPPost erpPost = new ERPPost(jNewPost.getString("_id"), jNewPost.getString("info"),
                        jNewPost.getString("title"), jNewPost.getString("createdOn"),
                        wall, false, jNewPost.getJSONArray("comments").toString(), "[]",
                        jNewPost.getString("updatedOn"), jNewPost.getString("createdBy"));
                erpPost.SavePost(NewPostActivity.this);
                if (listener != null ) listener.onNewPostCreated(erpPost);
                Log.d(LOG_TAG, jNewPost.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            if (status/100 == 2){
                NewPostActivity.this.finish();
            }

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

    public interface NewPostListener{
        void onNewPostCreated(ERPPost erpPost);
    }

    public static void setOnNewPostListener(NewPostListener newPostListener){
        listener = newPostListener;
    }
}

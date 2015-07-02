package org.saarang.erp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.saarang.erp.R;

public class NewPostActivity extends AppCompatActivity {

    String[] departements = {"Finance", "Publicity", "Design and Media", "Marketing and Sales",
            "Mobile Operations", "Web Operations"};

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, departements);
        final AutoCompleteTextView tvDep = (AutoCompleteTextView)
                findViewById(R.id.tvDeps);
        tvDep.setAdapter(adapter);

        etTitle = (EditText)findViewById(R.id.etTitle);
        etBody = (EditText)findViewById(R.id.etBody);
        etBody = (EditText) findViewById(R.id.etBody);
        etTitle = (EditText) findViewById(R.id.etTitle);


        Button bDone = (Button)findViewById(R.id.bDone);
        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wallSelected = tvDep.getText().toString();
                getId(wallSelected);
                Log.d("ID", id);
                String title = etTitle.getText().toString();
                String body = etBody.getText().toString();
            }
        });

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

    //get list of walls as a String array
    public void makeArray(){
        for (int i = 0; i<length; i++){
            departements[i] = wallList.get(i).getName();
        }
    }

    //method to get id of wall
    public void getId(String s){
        for (int i = 0; i<length; i++){
            if(wallList.get(i).getName().equalsIgnoreCase(s))
                id = wallList.get(i).getId();
        }
    }
}

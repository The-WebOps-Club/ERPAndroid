package org.saarang.erp.Activities;

import android.content.Intent;
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

import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.Objects.ERPWall;
import org.saarang.erp.R;

import java.util.ArrayList;
import java.util.List;

public class NewPostActivity extends AppCompatActivity {

    String[] departements;
    int length;
    String id;
    EditText etTitle;
    EditText etBody;
    ArrayList<ERPWall> wallList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_new_post);

        Log.d("ARRAY", ERPProfile.getUserWalls(this));
        Toolbar tb = (Toolbar)findViewById(R.id.toolbarNewPost);
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

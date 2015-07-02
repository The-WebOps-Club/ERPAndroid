package org.saarang.erp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        Toolbar tb = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tb);


        Intent intent = getIntent();

        //Add array adapter for dropdown edit text
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, departements);
        AutoCompleteTextView textView = (AutoCompleteTextView)
                findViewById(R.id.tvDeps);
        textView.setAdapter(adapter);


    }


}

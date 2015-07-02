package org.saarang.erp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import org.saarang.erp.R;

public class NewPostActivity extends ActionBarActivity {

    String[] departements = {"Finance", "Publicity", "Design and Media", "Marketing and Sales",
            "Mobile Operations", "Web Operations"};

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

}

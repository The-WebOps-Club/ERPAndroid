package org.saarang.erp.Activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import org.saarang.erp.Adapters.PeopleAdapter;
import org.saarang.erp.Adapters.SinglePostAdapter;
import org.saarang.erp.R;

public class SinglePostActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_post);
        Toolbar tb = (Toolbar)findViewById(R.id.tbSinglePost);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationIcon(R.drawable.ic_arrow_left);

        RecyclerView rvSinglePost = (RecyclerView)findViewById(R.id.rvSinglePost);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvSinglePost.setLayoutManager(layoutManager);

        // set the adapter
        SinglePostAdapter adapter = new SinglePostAdapter(this);
        rvSinglePost.setAdapter(adapter);
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

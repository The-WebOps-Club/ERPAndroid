package org.saarang.erp.Activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import org.saarang.erp.Adapters.NewsFeedAdapter;
import org.saarang.erp.Helper.DatabaseHelper;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajmal on 03-07-2015.
 */
public class WallActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<ERPPost> arrayList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    String postId, wallName;
    ImageButton ibNewPost;

    private static String LOG_TAG = "WallActivity";
    public static String EXTRA_WALLID = "wallId";
    public static String EXTRA_WALL_NAME= "wallName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_wall);


        postId = getIntent().getExtras().getString(EXTRA_WALLID);
        wallName = getIntent().getExtras().getString(EXTRA_WALL_NAME);
        recyclerView = (RecyclerView) findViewById(R.id.rvWallActivity);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        //Adding toolbar
        Toolbar tb = (Toolbar)findViewById(R.id.toolbarWall);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        tb.setNavigationIcon(R.drawable.ic_arrow_left);
        ibNewPost = (ImageButton)findViewById(R.id.ibPlus);

        //Set title of the Wall
        setTitle(wallName);

        //Setting floating action button
        final Intent intentPost = new Intent(this, NewPostActivity.class);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabWallActivity);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3F51B5")));
        ibNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                intentPost.putExtra("wall", wallName);
//                startActivity(intentPost);
                Intent intent = new Intent(WallActivity.this, NewPostActivity.class);
                intent.putExtra(NewPostActivity.EXTRA_WALLNAME, wallName);
                intent.putExtra(NewPostActivity.EXTRA_WALLID, postId);
                startActivity(intent);
            }
        });


        // Getting posts
        arrayList = new DatabaseHelper(this).getPostsFromWall(postId);

        adapter = new NewsFeedAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

    }

    //On Click of back navigation button finish() this activity
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;

    }
    @Override
    public void onRefresh() {

    }
}

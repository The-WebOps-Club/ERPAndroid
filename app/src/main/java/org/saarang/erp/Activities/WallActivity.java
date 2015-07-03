package org.saarang.erp.Activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    String postId;

    private static String LOG_TAG = "WallActivity";
    public static String EXTRA_WALLID = "wallId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ac_wall);

        postId = getIntent().getExtras().getString(EXTRA_WALLID);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

//        //Random dataset
//        arrayList = RandomGenerator.getRandomPosts(15);

        arrayList = new DatabaseHelper(this).getPostsFromWall(postId);

        adapter = new NewsFeedAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onRefresh() {

    }
}

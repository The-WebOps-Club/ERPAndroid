package org.saarang.erp.Activities;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.analytics.Tracker;

import org.saarang.erp.Adapters.CommentsViewPagerAdapter;
import org.saarang.erp.Helper.DatabaseHelper;
import org.saarang.erp.R;
import org.saarang.saarangsdk.Views.NonSwipeableViewPager;

public class CommentsActivity extends FragmentActivity {

    CommentsViewPagerAdapter pageAdapter;
    String comments, acknowledgment, postId, postDate;
    public NonSwipeableViewPager mpager;

    public static String EXTRA_COMMENTS = "comments";
    public static String EXTRA_ACKNOWLEDGMENTS = "acknowledgments";
    public static String EXTRA_POSTID = "postId";

    private static String LOG_TAG = "CommentsActivity";

    @Override
    protected void onStart() {
        super.onStart();
        ((AnalyticsApp)getApplication()).getAnalytics().reportActivityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((AnalyticsApp)getApplication()).getAnalytics().reportActivityStop(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_comments);

        ((AnalyticsApp)getApplication()).getDefaultTracker();

        comments = getIntent().getStringExtra(EXTRA_COMMENTS);
//        acknowledgment = getIntent().getStringExtra(EXTRA_ACKNOWLEDGMENTS);
        postId = getIntent().getStringExtra(EXTRA_POSTID);

//        List<ERPComment> list = ERPComment.getCommentsFromString(comments);
        DatabaseHelper data = new DatabaseHelper(this);
        acknowledgment = data.getAcknowledgment(postId);

        pageAdapter = new CommentsViewPagerAdapter(getSupportFragmentManager(), postId, acknowledgment);
        mpager=(NonSwipeableViewPager)findViewById(R.id.vpComments);
        mpager.setAdapter(pageAdapter);


    }

    public void switchFragment(int target){
        mpager.setCurrentItem(target);
    }

}
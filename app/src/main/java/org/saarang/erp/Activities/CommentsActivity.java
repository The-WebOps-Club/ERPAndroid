package org.saarang.erp.Activities;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import org.saarang.erp.Adapters.CommentsViewPagerAdapter;
import org.saarang.erp.Fragments.CommentsFragment;
import org.saarang.erp.R;
import org.saarang.saarangsdk.Views.NonSwipeableViewPager;

public class CommentsActivity extends FragmentActivity implements CommentsFragment.CommentsListener{

    CommentsViewPagerAdapter pageAdapter;
    String comments, acknowledgment, postId, postDate;
    public NonSwipeableViewPager mpager;

    public static String EXTRA_COMMENTS = "comments";
    public static String EXTRA_ACKNOWLEDGMENTS = "acknowledgments";
    public static String EXTRA_POSTID = "postId";

    private static String LOG_TAG = "CommentsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_comments);

        comments = getIntent().getStringExtra(EXTRA_COMMENTS);
        acknowledgment = getIntent().getStringExtra(EXTRA_ACKNOWLEDGMENTS);
        postId = getIntent().getStringExtra(EXTRA_POSTID);

//        List<ERPComment> list = ERPComment.getCommentsFromString(comments);
//


        pageAdapter = new CommentsViewPagerAdapter(getSupportFragmentManager(), postId, acknowledgment);
        mpager=(NonSwipeableViewPager)findViewById(R.id.vpComments);
        mpager.setAdapter(pageAdapter);

        CommentsFragment.setOnChangeListener(this);

    }

    public void switchFragment(int target){
        mpager.setCurrentItem(target);
    }


    @Override
    public void onCommentsAdded() {
        mpager.setCurrentItem(0);
        Log.d(LOG_TAG, "Comment added");
    }
}
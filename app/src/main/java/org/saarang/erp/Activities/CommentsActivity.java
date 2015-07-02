package org.saarang.erp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.saarang.erp.Adapters.CommentsViewPagerAdapter;
import org.saarang.erp.Objects.ERPComment;
import org.saarang.erp.R;
import org.saarang.saarangsdk.Views.NonSwipeableViewPager;

import java.util.List;

public class CommentsActivity extends FragmentActivity {

    CommentsViewPagerAdapter pageAdapter;
    String comments, acknowledgment, postId;
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

        List<ERPComment> list = ERPComment.getCommentsFromString(comments);

        pageAdapter = new CommentsViewPagerAdapter(getSupportFragmentManager(), postId, comments, acknowledgment);
        mpager=(NonSwipeableViewPager)findViewById(R.id.vpComments);
        mpager.setAdapter(pageAdapter);

    }

    public void switchFragment(int target){
        mpager.setCurrentItem(target);
    }

}
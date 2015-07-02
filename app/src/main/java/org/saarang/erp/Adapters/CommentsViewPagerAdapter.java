package org.saarang.erp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.saarang.erp.Fragments.CommentsFragment;
import org.saarang.erp.Fragments.CommentsSecondFragment;

/**
 * Created by Ajmal on 01-07-2015.
 */
public class CommentsViewPagerAdapter extends FragmentStatePagerAdapter {

    String comments, acknowledgment, postId;


    public CommentsViewPagerAdapter(FragmentManager fm, String postId, String comments, String acknowledgment)
    {
        super(fm);
        this.comments = comments;
        this.acknowledgment = acknowledgment;
        this.postId = postId;
    }
    @Override
    public int getCount()
    {
        return 2;
    }
    @Override
    public Fragment getItem(int position)
    {
        switch(position)
        {
            case 0: return new CommentsFragment().newInstance(postId, comments);
            case 1: return new CommentsSecondFragment();
            default : return new CommentsFragment().newInstance(postId, comments);
        }
    }
}
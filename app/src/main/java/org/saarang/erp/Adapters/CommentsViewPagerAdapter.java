package org.saarang.erp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.saarang.erp.Fragments.CommentsFragment;
import org.saarang.erp.Fragments.CommentsSecondFragment;
import org.saarang.erp.Objects.ERPAcknowledged;

/**
 * Created by Ajmal on 01-07-2015.
 */
public class CommentsViewPagerAdapter extends FragmentStatePagerAdapter {

    String  acknowledgment, postId;
    int acknoNum;

    public CommentsViewPagerAdapter(FragmentManager fm, String postId, String acknowledgment)
    {
        super(fm);
        this.acknowledgment = acknowledgment;
        this.postId = postId;
        this.acknoNum = ERPAcknowledged.getAcknowledgedFromString(acknowledgment).size();
    }
    @Override
    public int getCount()
    {
        return 2;
    }
    @Override
    public Fragment getItem(int position)
    {
       // List<ERPAcknowledged> acknoList = ERPAcknowledged.getAcknowledgedFromString(acknowledgment);

        switch(position)
        {
            case 0: return new CommentsFragment().newInstance(postId,  acknoNum);
            case 1: return new CommentsSecondFragment().newInstance(postId, acknowledgment);
            default : return new CommentsFragment().newInstance(postId, acknoNum);
        }
    }
}
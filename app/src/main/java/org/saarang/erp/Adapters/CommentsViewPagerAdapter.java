package org.saarang.erp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.saarang.erp.Fragments.CommentsFirstFragment;
import org.saarang.erp.Fragments.CommentsSecondFragment;

/**
 * Created by Moochi on 01-07-2015.
 */
public class CommentsViewPagerAdapter extends FragmentStatePagerAdapter {
    public CommentsViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
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
            case 0: return new CommentsFirstFragment();
            //case 1: return new CommentsSecondFragment();
            default : return new CommentsFirstFragment();
        }
    }
}
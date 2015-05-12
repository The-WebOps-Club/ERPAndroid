package org.saarang.erp.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.saarang.erp.Fragment.NewsFeedFragment;
import org.saarang.erp.Fragment.NotificationsFragment;
import org.saarang.erp.Fragment.PeopleFragment;
import org.saarang.erp.Fragment.WallsFragment;

/**
 * Created by aqel on 8/5/15.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {

            case 0: return new NewsFeedFragment();
            case 1: return new NotificationsFragment();
            case 2: return new PeopleFragment();
            case 3: return new WallsFragment();
            default: return new NewsFeedFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

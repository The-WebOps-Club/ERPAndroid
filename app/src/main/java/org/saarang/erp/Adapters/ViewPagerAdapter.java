package org.saarang.erp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.saarang.erp.Fragments.NewsFeedFragment;
import org.saarang.erp.Fragments.NotificationsFragment;
import org.saarang.erp.Fragments.PeopleFragment;
import org.saarang.erp.Fragments.WallsFragment;

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
            case 2: return new WallsFragment();
            case 3: return new PeopleFragment();
            default: return new NewsFeedFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}

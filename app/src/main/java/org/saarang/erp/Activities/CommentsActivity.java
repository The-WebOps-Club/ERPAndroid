package org.saarang.erp.Activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import org.saarang.erp.Adapters.CommentsViewPagerAdapter;
import org.saarang.erp.R;
import org.saarang.saarangsdk.Views.NonSwipeableViewPager;

public class CommentsActivity extends FragmentActivity {


    CommentsViewPagerAdapter pageAdapter;

    public NonSwipeableViewPager mpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_comments);

         pageAdapter = new CommentsViewPagerAdapter(getSupportFragmentManager());
         mpager=(NonSwipeableViewPager)findViewById(R.id.vpComments);
         mpager.setAdapter(pageAdapter);


    }

    public void switchFragment(int target){
        mpager.setCurrentItem(target);
    }

}
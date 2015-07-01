package org.saarang.erp.Activities;


import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import org.saarang.erp.Adapters.CommentsAdapter;
import org.saarang.erp.Adapters.CommentsViewPagerAdapter;
import org.saarang.erp.R;

public class CommentsActivity extends FragmentActivity {


    CommentsViewPagerAdapter pageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_comments);

       pageAdapter = new CommentsViewPagerAdapter(getSupportFragmentManager());
        ViewPager mpager=(ViewPager)findViewById(R.id.vpComments);
        mpager.setAdapter(pageAdapter);

      /*  tvBack = (TextView) findViewById(R.id.bBack);
        tvBack.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }));
*/
    }

}
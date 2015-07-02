package org.saarang.erp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.saarang.erp.Activities.CommentsActivity;
import org.saarang.erp.Adapters.CommentsFirstAdapter;
import org.saarang.erp.R;
import org.saarang.saarangsdk.Views.NonSwipeableViewPager;

/**
 * Created by Ajmal on 01-07-2015.
 */
public class CommentsFirstFragment extends Fragment {

    int commentsLayout[] = {R.layout.fr_comments, R.layout.fr_comments_blank};
    int position = 0;

    NonSwipeableViewPager mpager;

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ImageView ivNext;
    TextView tvBack;
    View rootView;

    // decide position according to number of people commented/ acknowledged is zero or not.
    // and set the layout accordingly.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (position == 0) {

            rootView = inflater.inflate(commentsLayout[position], container, false);
            setRecycler(rootView);

        }
        else {
            rootView = inflater.inflate(commentsLayout[position], container, false);
        }

        ivNext=(ImageView)rootView.findViewById(R.id.ivNextPage);

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CommentsActivity)getActivity()).switchFragment(1);
                //switchFragment() is the custom defined function in the Activity where Viewpager is defined.
            }
        });

        tvBack = (TextView) rootView.findViewById(R.id.bBack);
        tvBack.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (getActivity()).finish();
            }
        }));
        return rootView;
    }

    public void setRecycler(View rootView) {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvComments);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentsFirstAdapter(getActivity());
        recyclerView.setAdapter(adapter);


    }

}
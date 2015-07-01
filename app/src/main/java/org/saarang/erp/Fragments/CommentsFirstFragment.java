package org.saarang.erp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import org.saarang.erp.Adapters.CommentsAdapter;
import org.saarang.erp.R;

/**
 * Created by Moochi on 01-07-2015.
 */
public class CommentsFirstFragment extends Fragment {

    int commentsLayout[] = {R.layout.fr_comments, R.layout.fr_comments_blank};
    int position = 0;

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ImageView ivNext;
    View rootView;

       // decide position according to number of people commented/ acknowledged is zero or not
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (position == 0) {

            rootView = inflater.inflate(commentsLayout[position], container, false);
            setRecycler(rootView);

        } else {
            rootView = inflater.inflate(commentsLayout[position], container, false);

        }
        ivNext=(ImageView)rootView.findViewById(R.id.ivNextPage);

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return rootView;
    }

    public void setRecycler(View rootView) {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvComments);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentsAdapter(getActivity());
        recyclerView.setAdapter(adapter);


    }

}
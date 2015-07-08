package org.saarang.erp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.saarang.erp.Activities.CommentsActivity;
import org.saarang.erp.Adapters.CommentsSecondAdapter;
import org.saarang.erp.Objects.ERPAcknowledged;
import org.saarang.erp.R;

import java.util.List;

/**
 * Created by Ajmal on 01-07-2015.
 */
public class CommentsSecondFragment extends Fragment {


    int acknoLayouts[] = {R.layout.fr_ackno, };
    int position = 0;
    View rootView;

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private static String ARG_ACKNOWLEDGEMENT="arg_ackno";
    private static String ARG_POSTID = "arg_postId";

    String acknowledged, postId;
    List<ERPAcknowledged> acknowledgedList;

    ImageView ivPrev;
    // decide position according to number of people commented/ acknowledged is zero or not
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Another Safety check :P
        if (getArguments() == null){
            rootView = inflater.inflate(R.layout.fr_ackno_blank, container, false);
            return rootView;
        }

        // Post Id
        postId = getArguments().getString(ARG_POSTID);

        // Retrieving acknowledgements from argument
        acknowledged = getArguments().getString(ARG_ACKNOWLEDGEMENT);
        acknowledgedList = ERPAcknowledged.getAcknowledgedFromString(acknowledged);

        // Checking if there are people acknowledged
        if (acknowledgedList.size() == 0) {
             rootView = inflater.inflate(R.layout.fr_ackno_blank, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fr_ackno, container, false);
            setRecycler(rootView);
        }

        // Previous page of ViewPager
        ivPrev=(ImageView)rootView.findViewById(R.id.ivPrevPage);

        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CommentsActivity)getActivity()).switchFragment(0);
                //  Ruined my whole night
                //switchFragment() is the custom defined function in the Activity where Viewpager is
                //defined .
            }
        });


        return rootView;

    }

    public Fragment newInstance(String postId, String acknowledgement){
        CommentsSecondFragment fragment = new CommentsSecondFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ACKNOWLEDGEMENT, acknowledgement);
        args.putString(ARG_POSTID, postId);
        fragment.setArguments(args);
        return fragment;
    }

    public void setRecycler(View rootView) {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvAckno);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentsSecondAdapter(getActivity(), acknowledgedList);
        recyclerView.setAdapter(adapter);


    }
}

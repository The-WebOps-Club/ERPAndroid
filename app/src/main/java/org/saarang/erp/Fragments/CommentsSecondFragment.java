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
import org.saarang.erp.R;

/**
 * Created by Ajmal on 01-07-2015.
 */
public class CommentsSecondFragment extends Fragment {


    int acknoLayout[] = {R.layout.fr_ackno, R.layout.fr_ackno_blank};
    int position = 0;
    View rootView;

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    ImageView ivPrev;
    // decide position according to number of people commented/ acknowledged is zero or not
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (position == 0) {

             rootView = inflater.inflate(acknoLayout[position], container, false);
            setRecycler(rootView);

        } else {
            rootView = inflater.inflate(acknoLayout[position], container, false);
        }


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

    public void setRecycler(View rootView) {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvAckno);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentsSecondAdapter(getActivity());
        recyclerView.setAdapter(adapter);


    }
}

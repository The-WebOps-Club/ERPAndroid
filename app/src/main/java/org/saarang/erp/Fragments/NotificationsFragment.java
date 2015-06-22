package org.saarang.erp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.erp.Adapters.NotificationsAdapter;
import org.saarang.erp.R;

/**
 * Created by aqel on 8/5/15.
 */
public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    public NotificationsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_notification, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        //Random dataset
//        arrayList = RandomGenerator.getRandomPosts(15);

        adapter = new NotificationsAdapter();
        recyclerView.setAdapter(adapter);


        return rootView;
    }
}
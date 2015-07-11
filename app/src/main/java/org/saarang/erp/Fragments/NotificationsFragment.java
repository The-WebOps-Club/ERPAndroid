package org.saarang.erp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.erp.Adapters.NotificationsAdapter;
import org.saarang.erp.Helper.DatabaseHelper;
import org.saarang.erp.Objects.ERPNotification;
import org.saarang.erp.R;
import org.saarang.erp.Utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqel on 8/5/15.
 */
public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<ERPNotification> arrayList = new ArrayList<>();


    public NotificationsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_notification, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);


        //added extra for setting divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DatabaseHelper data = new DatabaseHelper(getActivity());
        arrayList = data.getAllNotifications();

//        //Random dataset
//        arrayList = RandomGenerator.getRandomPosts(15);

        adapter = new NotificationsAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);


        return rootView;
    }
}
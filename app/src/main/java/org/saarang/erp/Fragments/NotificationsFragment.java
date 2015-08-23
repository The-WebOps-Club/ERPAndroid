package org.saarang.erp.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    private static String LOG_TAG = "Notifications Fragment";

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<ERPNotification> arrayList = new ArrayList<>();
    TextView tvNotifications;
    ImageView ivNotifError;


    public NotificationsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_notification, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        tvNotifications = (TextView)rootView.findViewById(R.id.tvNotifications);
        ivNotifError = (ImageView)rootView.findViewById(R.id.ivNotifError);


        //added extra for setting divider
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


//        //Random dataset
//        arrayList = RandomGenerator.getRandomPosts(15);





        DatabaseHelper data = new DatabaseHelper(getActivity());
        arrayList = data.getAllNotifications();
        Log.d(LOG_TAG, "stored notifs: " + arrayList);

        if(arrayList.isEmpty()){
            Log.d(LOG_TAG, "No notifications");
            ivNotifError.setVisibility(View.VISIBLE);
            tvNotifications.setVisibility(View.VISIBLE);
        }
        else {
            Log.d(LOG_TAG, "Notifications present");
            tvNotifications.setVisibility(View.GONE);
            ivNotifError.setVisibility(View.GONE);

        }

        adapter = new NotificationsAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
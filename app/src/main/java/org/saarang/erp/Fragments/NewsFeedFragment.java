package org.saarang.erp.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.saarang.erp.Adapters.NewsFeedAdapter;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.R;
import org.saarang.erp.Utils.RandomGenerator;

import java.util.ArrayList;

/**
 * Created by aqel on 8/5/15.
 */
public class NewsFeedFragment extends Fragment{

    public NewsFeedFragment() {
    }

    ImageView impic;
    ImageButton ibCall;
    ImageButton ibMail;
    ImageButton ibProfile;
    RecyclerView recyclerView;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading = true;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ERPPost> arrayList = new ArrayList<>();

    SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fr_news_feed, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Random dataset
        arrayList = RandomGenerator.getRandomPosts(15);

        adapter = new NewsFeedAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
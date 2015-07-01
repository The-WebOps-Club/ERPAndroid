package org.saarang.erp.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
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
import org.saarang.erp.Helper.DatabaseHelper;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqel on 8/5/15.
 */
public class NewsFeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public NewsFeedFragment() {
    }

    ImageView impic;
    ImageButton ibCall;
    ImageButton ibMail;
    ImageButton ibProfile;
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<ERPPost> arrayList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;

    private static String LOG_TAG = "NewsFeedFragment";


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_news_feed, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

//        //Random dataset
//        arrayList = RandomGenerator.getRandomPosts(15);

        arrayList = new DatabaseHelper(getActivity()).getAllPosts();

        adapter = new NewsFeedAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    public void onRefresh() {
        Log.d(LOG_TAG, "Refresh");
        swipeRefreshLayout.setRefreshing(false);
    }

    private class getNewsFeed extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

    }
}
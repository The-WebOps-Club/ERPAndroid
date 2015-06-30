package org.saarang.erp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class NewsFeedFragment extends Fragment {

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


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_news_feed, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

//        //Random dataset
//        arrayList = RandomGenerator.getRandomPosts(15);

        arrayList = new DatabaseHelper(getActivity()).getAllPosts();

        adapter = new NewsFeedAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
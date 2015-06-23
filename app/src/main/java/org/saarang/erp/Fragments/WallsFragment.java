package org.saarang.erp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.erp.Adapters.WallsAdapter;
import org.saarang.erp.R;
import org.saarang.erp.Utils.DividerItemDecoration;

/**
 * Created by aqel on 8/5/15.
 */
public class WallsFragment extends Fragment {

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    public WallsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_walls, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);


        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new WallsAdapter();

        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
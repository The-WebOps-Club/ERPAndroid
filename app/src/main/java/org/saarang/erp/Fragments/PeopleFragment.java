package org.saarang.erp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.erp.Adapters.PeopleAdapter;
import org.saarang.erp.R;

/**
 * Created by aqel on 8/5/15.
 */
public class PeopleFragment extends Fragment {
    RecyclerView rvPeople;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    String[] departements = {"Finance", "Publicity", "Design and Media", "Marketing and Sales",
            "Mobile Operations", "Web Operations"};

    public PeopleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_people, container, false);

        rvPeople = (RecyclerView)rootView.findViewById(R.id.rvPeople);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        rvPeople.setLayoutManager(layoutManager);

        // set the adapter
        PeopleAdapter adapter = new PeopleAdapter(getActivity());
        rvPeople.setAdapter(adapter);
        return rootView;
    }
}
package org.saarang.erp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.saarang.erp.Adapters.PeopleAdapter;
import org.saarang.erp.Adapters.WallsAdapter;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.Objects.ERPWall;
import org.saarang.erp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqel on 8/5/15.
 */
public class WallsFragment extends Fragment {

    RecyclerView rvWalls;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Gson gson = new Gson();
    ArrayList<ERPWall> wallList;
    public WallsFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_walls, container, false);
        // Fetching the accessible list of Walls for the user.
        wallList = gson.fromJson(ERPProfile.getUserWalls(this.getActivity()),new TypeToken<List<ERPWall>>(){}.getType());


        //Initializing Recycler View walls.
        rvWalls = (RecyclerView)rootView.findViewById(R.id.rvWalls);

        //Using a linear layout manager

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rvWalls.setLayoutManager(lm);

        //Setting Apapter
        WallsAdapter walls= new WallsAdapter(getActivity(),wallList);
        rvWalls.setAdapter(walls);

        return rootView;
    }
}
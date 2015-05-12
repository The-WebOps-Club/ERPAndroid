package org.saarang.erp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.erp.R;

/**
 * Created by aqel on 8/5/15.
 */
public class WallsFragment extends Fragment {

    public WallsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fr_walls, container, false);
        return rootView;
    }
}
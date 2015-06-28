package org.saarang.erp.Fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import org.saarang.erp.R;

public class CommentsFragment extends DialogFragment {


    public CommentsFragment() {
        // Required empty public constructor
    }

    public static CommentsFragment newInstance() {
        return new CommentsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_comments, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }


}

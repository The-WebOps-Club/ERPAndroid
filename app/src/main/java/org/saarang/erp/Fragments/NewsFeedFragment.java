package org.saarang.erp.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import org.saarang.erp.R;

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

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_news_feed, container, false);

        impic = (ImageView)rootView.findViewById(R.id.imPic);

        impic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View dialoglayout = inflater.inflate(R.layout.alert_profile_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(dialoglayout);
//                ibCall = (ImageButton)dialoglayout.findViewById(R.id.ibCall);
//                ibMail = (ImageButton)dialoglayout.findViewById(R.id.ibMail);
//                ibProfile = (ImageButton)dialoglayout.findViewById(R.id.ibProfile);

                Resources r = getResources();
                int px = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, r.getDisplayMetrics());
                builder.show().getWindow().setLayout(px, ViewGroup.LayoutParams.WRAP_CONTENT);
//                builder.show();
            }
        });
        return rootView;
    }
}
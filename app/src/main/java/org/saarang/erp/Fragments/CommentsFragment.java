package org.saarang.erp.Fragments;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.saarang.erp.Activities.CommentsActivity;
import org.saarang.erp.Adapters.CommentsAdapter;
import org.saarang.erp.Objects.ERPComment;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;
import org.saarang.saarangsdk.Views.NonSwipeableViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ajmal on 01-07-2015.
 */
public class CommentsFragment extends Fragment {

    NonSwipeableViewPager mpager;

    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ImageView ivNext, ivSend;
    TextView tvBack;
    EditText etComment;

    View rootView;
    private static String ARG_COMMENT = "arg_comment";
    private static String ARG_POSTID = "arg_postId";
    private static String LOG_TAG = "CommentsFragment";
    String comments, postId;
    List<ERPComment> commentsList;
    AddComment addComment;

    // decide position according to number of people commented/ acknowledged is zero or not
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Safty check :P
        if (getArguments() == null){
            rootView = inflater.inflate(R.layout.fr_comments_blank, container, false);
            return rootView;
        }

        // Retrieving comment from argument
        comments = getArguments().getString(ARG_COMMENT);
        commentsList = ERPComment.getCommentsFromString(comments);

        // Post Id
        postId = getArguments().getString(ARG_POSTID);

        // Checking if there are comments
        if (commentsList.size() == 0){
            rootView = inflater.inflate(R.layout.fr_comments_blank, container, false);
        } else {
            rootView = inflater.inflate(R.layout.fr_comments, container, false);
            setRecycler(rootView);
        }

        // Next Button
        ivNext=(ImageView)rootView.findViewById(R.id.ivNextPage);
        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CommentsActivity)getActivity()).switchFragment(1);
                // Ruined my whole night
                //switchFragment() is the custom defined function in the Activity where Viewpager is
                //defined
            }
        });

        // Back Butoon
        tvBack = (TextView) rootView.findViewById(R.id.bBack);
        tvBack.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (getActivity()).finish();
            }
        }));

        ivSend = (ImageView) rootView.findViewById(R.id.ivSend);
        etComment = (EditText) rootView.findViewById(R.id.etComment);

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected()){
                    String comment = etComment.getText().toString();
                    if (comment.isEmpty()) return;
                    addComment = new AddComment();
                    addComment.execute(comment);
                }else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return rootView;
    }

    public void setRecycler(View rootView) {

        recyclerView = (RecyclerView) rootView.findViewById(R.id.rvComments);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CommentsAdapter(getActivity(), commentsList);
        recyclerView.setAdapter(adapter);
    }

    public Fragment newInstance(String postId, String comments){
        CommentsFragment fragment = new CommentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_COMMENT, comments);
        args.putString(ARG_POSTID, postId);
        fragment.setArguments(args);
        return fragment;
    }

    private class AddComment extends AsyncTask<String, Void, Void>{

        ProgressDialog pDialog;
        String comment;
        JSONObject json;
        ArrayList<PostParam> params;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Logging in ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... param) {
            comment = param[0];
            params = new ArrayList<>();
            params.add(new PostParam("postId", postId));
            params.add(new PostParam("comment", comment));
            json = PostRequest.execute(URLConstants.URL_POST_COMMENT_ADD, params, ERPProfile.getERPUserToken(getActivity()));
            Log.d(LOG_TAG, json.toString());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
            etComment.setText("");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ( addComment!= null )
            addComment.cancel(true);
    }

}
package org.saarang.erp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Adapters.CommentsAdapter;
import org.saarang.erp.Adapters.PeopleAdapter;
import org.saarang.erp.Adapters.SinglePostAdapter;
import org.saarang.erp.Helper.DatabaseHelper;
import org.saarang.erp.Objects.ERPComment;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;
import java.util.List;

public class SinglePostActivity extends ActionBarActivity {

    static ERPPost post;
    static List<ERPComment> commentsList;
    static Context mContext;
    static RecyclerView rvSinglePost;
    static LinearLayoutManager layoutManager;
    static SinglePostAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_post);
        Toolbar tb = (Toolbar)findViewById(R.id.tbSinglePost);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationIcon(R.drawable.ic_arrow_left);
        mContext = this;

        DatabaseHelper data = new DatabaseHelper(this);
        post = data.getRandomPost();
        commentsList = ERPComment.getCommentsFromString(post.getComments());
        Log.d("COMMENTS LIST", commentsList.toString());

        rvSinglePost = (RecyclerView)findViewById(R.id.rvSinglePost);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        rvSinglePost.setLayoutManager(layoutManager);

        // set the adapter
        adapter = new SinglePostAdapter(this, post, commentsList);
        rvSinglePost.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public static class AddComment extends AsyncTask<String, Void, Void> {

        ProgressDialog pDialog;
        String comment;
        JSONObject json;
        ArrayList<PostParam> params;
        int status = 989;
        String newComments;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(mContext);
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... param) {
            comment = param[0];
            params = new ArrayList<>();
            params.add(new PostParam("postId", post.getPostId()));
            params.add(new PostParam("comment", comment));
            json = PostRequest.execute(URLConstants.URL_POST_COMMENT_ADD, params, ERPProfile.getERPUserToken(mContext));
            try {
                status = json.getInt("status");
                if (status == 200){
                    newComments = json.getJSONObject("data").getJSONArray("comments").toString();
                    DatabaseHelper data = new DatabaseHelper(mContext);
                    data.updateComment(post.getPostId(), newComments );
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
            if (mContext == null)
                return;
            //etComment.setText("");
            if (status == 200){
                commentsList = ERPComment.getCommentsFromString(newComments);
                rvSinglePost.setLayoutManager(layoutManager);
                adapter = new SinglePostAdapter(mContext, post, commentsList);
                rvSinglePost.setAdapter(adapter);
            }
        }
    }
}

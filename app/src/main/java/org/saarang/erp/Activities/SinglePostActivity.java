package org.saarang.erp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Adapters.SinglePostAdapter;
import org.saarang.erp.Helper.DatabaseHelper;
import org.saarang.erp.Objects.ERPComment;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.Objects.ERPUser;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;
import java.util.List;

public class SinglePostActivity extends AppCompatActivity {

    static ERPPost post;
    static List<ERPComment> commentsList;
    static Context mContext;
    static RecyclerView rvSinglePost;
    static LinearLayoutManager layoutManager;
    static SinglePostAdapter adapter;
    private static String comments;
    private static String postId;
    private static Intent intent;
    static View view;


    public static String EXTRA_POSTID = "postId";
    private static String LOG_TAG = "SinglePostActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_post);

        view = findViewById(R.id.layout);
        Toolbar tb = (Toolbar)findViewById(R.id.tbSinglePost);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tb.setNavigationIcon(R.drawable.ic_arrow_left);
        mContext = this;



        // Getting post
        intent = getIntent();
        postId = intent.getExtras().getString(EXTRA_POSTID);
        Log.d(LOG_TAG, postId);

        //          Get the post from database
        DatabaseHelper data = new DatabaseHelper(this);
        post = data.getPost(postId);

//        post = data.getRandomPost();
//        postId = post.getPostId();

        //          Set title as the wall name
        getSupportActionBar().setTitle(post.getWall().getName());

        comments = post.getComments();
        commentsList = ERPComment.getCommentsFromString(comments);
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


public static class AddComment extends AsyncTask<String, Void, Void>{

    ProgressDialog pDialog;
    String comment;
    JSONObject json, jComment, jNewComment;
    ArrayList<PostParam> params;
    int status = 989;
    String newComments;
    Gson gson = new Gson();
    JSONArray jCommentsArray;
    List<ERPComment> newC;

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
        params.add(new PostParam("postId", postId));
        params.add(new PostParam("comment", comment));
        json = PostRequest.execute(URLConstants.URL_POST_COMMENT_ADD, params, ERPProfile.getERPUserToken(mContext));
        Log.d(LOG_TAG, json.toString());
        try {
            status = json.getInt("status");
            if (status/100 == 2){
                jComment = json.getJSONObject("data");
                JSONObject jComments = new JSONObject("{ \"comments\": " + comments + " }");
                jCommentsArray = jComments.getJSONArray("comments");
                ERPUser user = new ERPUser();
                user.set_id(ERPProfile.getERPUserId(mContext));
                user.setName(ERPProfile.getERPUserName(mContext));
                jNewComment = new JSONObject();
                jNewComment.put("_id", jComment.getString("_id"));
                jNewComment.put("info", jComment.getString("info"));
                jNewComment.put("createdOn", jComment.getString("createdOn"));
                jNewComment.put("createdBy", new JSONObject(gson.toJson(user)));
                jCommentsArray.put(jNewComment);
                DatabaseHelper data = new DatabaseHelper(mContext);
                data.updateComment(postId, jCommentsArray.toString() );
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
        if (status/100 == 2){
            commentsList = ERPComment.getCommentsFromString(jCommentsArray.toString());
            rvSinglePost.setLayoutManager(layoutManager);
            adapter = new SinglePostAdapter(mContext, post, commentsList);
            rvSinglePost.setAdapter(adapter);
            comments = jCommentsArray.toString();
        }
        showSnack(status);
    }
}

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    static void showSnack(int status){
        switch (status){
            case 500:
                UIUtils.showSnackBar(view, "There was an error connecting to our server. Please try again");
                break;
            case 401:
                UIUtils.showSnackBar(view, "You are not authorised to post on this wall");
                break;
            case 404:
                UIUtils.showSnackBar(view, "There was an error connecting to our server. Please " +
                        "check your internet connection and try again later");
                break;
            default:
                UIUtils.showSnackBar(view, "There was an error connecting to our server. Please try again");
                break;
        }
    }

}
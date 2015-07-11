package org.saarang.erp.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Activities.NewPostActivity;
import org.saarang.erp.Adapters.NewsFeedAdapter;
import org.saarang.erp.Helper.DatabaseHelper;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;
import org.saarang.erp.Utils.SPUtils;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aqel on 8/5/15.
 */
public class NewsFeedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        NewPostActivity.NewPostListener{

    public NewsFeedFragment() {
    }

    ImageView impic;
    ImageButton ibCall;
    ImageButton ibMail;
    ImageButton ibProfile;
    RecyclerView recyclerView;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    boolean loading = true;

    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<ERPPost> arrayList = new ArrayList<>();
    SwipeRefreshLayout swipeRefreshLayout;
    GetNewsFeedTask parseNewsFeed;
    View rootView;

    private static String LOG_TAG = "NewsFeedFragment";


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fr_news_feed, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

//        //Random dataset
//        arrayList = RandomGenerator.getRandomPosts(15);

        arrayList = new DatabaseHelper(getActivity()).getAllPosts();

        adapter = new NewsFeedAdapter(getActivity(), arrayList);
        recyclerView.setAdapter(adapter);

        parseNewsFeed = new GetNewsFeedTask();

        NewPostActivity.setOnNewPostListener(this);
        return rootView;
    }

    @Override
    public void onRefresh() {

        if (Connectivity.isConnected()){
            parseNewsFeed = new GetNewsFeedTask();
            parseNewsFeed.execute();
        }
        else{
            UIUtils.showSnackBar(getActivity().findViewById(android.R.id.content),
                    getActivity().getResources().getString(R.string.error_connection));
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onNewPostCreated(ERPPost erpPost) {
        Log.d(LOG_TAG, "Newpost created");
        if (getActivity() == null) return;
        arrayList.add(0, erpPost);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private class GetNewsFeedTask extends AsyncTask<Void, Void, Void> {

        JSONObject json;
        ArrayList<PostParam> params = new ArrayList<>();
        JSONArray jsonArray;

        @Override
        protected Void doInBackground(Void... voids) {

            Gson gson = new Gson();

            params.add(new PostParam("date", SPUtils.getLatestPostDate(getActivity())));

            try {
                json = PostRequest.execute(URLConstants.URL_NEWSFEED_REFRESH, params, ERPProfile.getERPUserToken(getActivity()));
                Log.d(LOG_TAG, "json response" + json.toString());

                // Get the time of latest post and save it to SP
                SPUtils.setLatestPostDate(getActivity(), json.getJSONObject("data").getJSONArray("response").getJSONObject(0).getString("updatedOn"));

                jsonArray = json.getJSONObject("data").getJSONArray("response");
                ERPPost.SavePosts(getActivity(),jsonArray);
            } catch (JSONException e){
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (getActivity() == null ) return;
            arrayList = new DatabaseHelper(getActivity()).getAllPosts();
            adapter = new NewsFeedAdapter(getActivity(), arrayList);
            recyclerView.setAdapter(adapter);

            swipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        parseNewsFeed.cancel(true);
    }
}
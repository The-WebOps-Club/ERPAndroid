package org.saarang.erp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Activities.CommentsActivity;
import org.saarang.erp.Activities.WallActivity;
import org.saarang.erp.Helper.DatabaseHelper;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Helpers.TimeHelper;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.PostRequest;
import org.saarang.saarangsdk.Objects.PostParam;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.callback.Callback;

/**
 * Created by Ahammad on 06/06/15.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {

    Context mContext;
    List<ERPPost> mItems;
    TimeHelper th=new TimeHelper();
    private static String LOG_TAG = "NewsFeedAdapter";

    public NewsFeedAdapter(Context context, List<ERPPost> items) {
        mContext = context;
        mItems = items;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvTitle, tvInfo, tvWall, tvPostDate;
        ImageView ivProfilePic;
        Button bComment, bAcknowledge;
        View mView;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvInfo = (TextView) view.findViewById(R.id.tvInfo);
            tvWall = (TextView) view.findViewById(R.id.tvWall);
            ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);
            bComment = (Button) view.findViewById(R.id.bComments);
            bAcknowledge = (Button) view.findViewById(R.id.bAcknowledge);
            mView = view.findViewById(android.R.id.content);
            tvPostDate= (TextView)view.findViewById(R.id.tvPostDate);

        }
    }

    public void UpdateAdapter(List<ERPPost> items){
        mItems = items;
    }

    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvName.setText(mItems.get(position).getPostedBy().getName());
        holder.tvPostDate.setText(th.getRelative(mItems.get(position).getCreatedOn()));
        holder.tvTitle.setText(mItems.get(position).getTitle());

        holder.tvInfo.setText(mItems.get(position).getInfo());
        holder.tvWall.setText(mItems.get(position).getWall().getName());

        final String profilePicUrl = URLConstants.SERVER + "api/users/" + mItems.get(position).getPostedBy().get_id() + "/profilePic";
        /*Glide.with(mContext)
                .load(profilePicUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_people)
                .crossFade()
                .into(holder.ivProfilePic);*/

        Picasso.with(mContext)
                .load(profilePicUrl)
                .placeholder(R.drawable.ic_people)
                .into(holder.ivProfilePic);




        Log.i("pic_id",profilePicUrl);

        /**
         * Personal wall and dept wall
         */
        holder.tvWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WallActivity.class);
                intent.putExtra(WallActivity.EXTRA_WALLID, mItems.get(position).getWall().get_id() );
                intent.putExtra(WallActivity.EXTRA_WALL_NAME, mItems.get(position).getWall().getName() );
                mContext.startActivity(intent);
            }
        });
        holder.tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WallActivity.class);
                intent.putExtra(WallActivity.EXTRA_WALLID, mItems.get(position).getPostedBy().get_id());
                intent.putExtra(WallActivity.EXTRA_WALL_NAME, mItems.get(position).getPostedBy().getName());
                mContext.startActivity(intent);
            }
        });

        /**
         * Alert dialog with contact details
         */
        holder.ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View dialoglayout = li.inflate(R.layout.alert_profile_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(dialoglayout);
                ImageView imageView = (ImageView) dialoglayout.findViewById(R.id.imageView);
                TextView tvName = (TextView) dialoglayout.findViewById(R.id.tvName);
                tvName.setText(mItems.get(position).getPostedBy().getName());
                /*Glide.with(mContext)
                        .load(profilePicUrl)
                        .centerCrop()
                        .placeholder(R.drawable.ic_people)
                        .crossFade()
                        .into(imageView);*/
                Picasso.with(mContext)
                        .load(profilePicUrl)
                        .placeholder(R.drawable.ic_people)
                        .into(imageView);

                builder.show();
            }
        });

        /**
         * Alert dialog for comment
         */
        holder.bComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(),CommentsActivity.class);
                myIntent.putExtra(CommentsActivity.EXTRA_COMMENTS, mItems.get(position).getComments());
                myIntent.putExtra(CommentsActivity.EXTRA_ACKNOWLEDGMENTS, mItems.get(position).getAcknowledge());
                myIntent.putExtra(CommentsActivity.EXTRA_POSTID, mItems.get(position).getPostId());
                view.getContext().startActivity(myIntent);
            }
        });

        if (mItems.get(position).isAcknowledged()){
            markAsAcknowledged(holder.bAcknowledge);
        }

        holder.bAcknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Connectivity.isConnected()){
                    new AcknowledgePost().execute( mItems.get(position).getPostId(), mItems.get(position).getAcknowledge() );
                    markAsAcknowledged(holder.bAcknowledge);
                }
                else{
                    UIUtils.showSnackBar(holder.tvName,
                            mContext.getResources().getString(R.string.error_connection));
                }
            }
        });

    }

    private void markAsAcknowledged(Button bAcknowledge) {
        bAcknowledge.setEnabled(false);
        bAcknowledge.setText("Acknowledged");
        bAcknowledge.setTextColor(mContext.getResources().getColor(R.color.indigo_color_disabled));
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private class AcknowledgePost extends AsyncTask<String, Void, Boolean>{

        JSONObject jsonObject;
        @Override
        protected Boolean doInBackground(String... params) {
            try {
                ArrayList<PostParam> param = new ArrayList<>();
                param.add(new PostParam("a", "Asd"));
                jsonObject = PostRequest.execute(URLConstants.URL_POST_ACKNOWLEDGE + params[0], param, ERPProfile.getERPUserToken(mContext));
                if (jsonObject.getInt("status") == 200){
                    DatabaseHelper data = new DatabaseHelper(mContext);
                    JSONArray jsonArray = new JSONArray(params[1]);
                    jsonArray.put(new JSONObject("{" +
                            "\"_id\":\""+ ERPProfile.getERPUserId(mContext) +  "\"," +
                            "\"name\": \"" + ERPProfile.getERPUserName(mContext) +"\"" +
                            "}"));
                    data.markPostAsUpdated(params[0], jsonArray.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }


}
package org.saarang.erp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.saarang.erp.Activities.CommentsActivity;
import org.saarang.erp.Helper.DatabaseHelper;
import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;
import org.saarang.erp.Utils.UIUtils;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Network.Connectivity;
import org.saarang.saarangsdk.Network.GetRequest;

import java.util.List;

/**
 * Created by Ahammad on 06/06/15.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>{

    Context mContext;
    List<ERPPost> mItems;
    private static String LOG_TAG = "NewsFeedAdapter";

    public NewsFeedAdapter(Context context, List<ERPPost> items) {
        mContext = context;
        mItems = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvTitle, tvInfo, tvWall;
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
        }
    }

    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        holder.tvName.setText(mItems.get(position).get);
        holder.tvTitle.setText(mItems.get(position).getTitle());
        String html = "<html><body style=\"text-align:justify\">" + mItems.get(position).getInfo() +  " </body></Html>\n" +
                "\n";
        holder.tvInfo.setText(Html.fromHtml(html));
        holder.tvWall.setText(mItems.get(position).getWall().getName());
//        Glide.with(mContext)
//                .load(mItems.get(position).getProfilePic())
//                .centerCrop()
//                .placeholder(R.drawable.ic_people)
//                .crossFade()
//                .into(holder.ivProfilePic);

        /**
         * Alert dialog with contact details
         */
        holder.ivProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                View dialoglayout = li.inflate(R.layout.alert_comments, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setView(dialoglayout);
                ImageView imageView = (ImageView) dialoglayout.findViewById(R.id.imageView);
                TextView tvName = (TextView) dialoglayout.findViewById(R.id.tvName);
//                tvName.setText(mItems.get(position).getPostedBy());
//                Glide.with(mContext)
//                        .load(mItems.get(position).getProfilePic())
//                        .centerCrop()
//                        .placeholder(R.drawable.ic_people)
//                        .crossFade()
//                        .into(imageView);

                builder.show();
            }
        });

        /**
         * Alert dialog for comment
         */
        holder.bComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent().setClass(view.getContext(),CommentsActivity.class);
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
                    new AcknowledgePost().execute( mItems.get(position).getPostId() );
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

    private class AcknowledgePost extends AsyncTask<String, Void, Void>{

        JSONObject jsonObject;
        @Override
        protected Void doInBackground(String... params) {
            jsonObject = GetRequest.execute(URLConstants.URL_POST_ACKNOWLEDGE + params[0], ERPProfile.getERPUserToken(mContext));
            try {
                if (jsonObject.getInt("status") == 200){
                    DatabaseHelper data = new DatabaseHelper(mContext);
                    data.markPostAsUpdated(params[0]);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
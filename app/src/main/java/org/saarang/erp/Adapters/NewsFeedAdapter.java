package org.saarang.erp.Adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.saarang.erp.Objects.ERPPost;
import org.saarang.erp.R;

import java.util.ArrayList;

/**
 * Created by Awanish Raj on 06/06/15.
 */

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>{

    Context mContext;
    ArrayList<ERPPost> mItems;

    public NewsFeedAdapter(Context context, ArrayList<ERPPost> items) {
        mContext = context;
        mItems = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvName, tvTitle, tvInfo, tvWall;
        ImageView ivProfilePic;

        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvInfo = (TextView) view.findViewById(R.id.tvInfo);
            tvWall = (TextView) view.findViewById(R.id.tvWall);
            ivProfilePic = (ImageView) view.findViewById(R.id.ivProfilePic);
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
        holder.tvName.setText(mItems.get(position).getPostedBy());
        holder.tvTitle.setText(mItems.get(position).getTitle());
        String html = "<html><body style=\"text-align:justify\">" + mItems.get(position).getInfo() +  " </body></Html>\n" +
                "\n";
        holder.tvInfo.setText(Html.fromHtml(html));
        holder.tvWall.setText(mItems.get(position).getWall());
        Glide.with(mContext)
                .load(mItems.get(position).getProfilePic())
                .centerCrop()
                .placeholder(R.drawable.ic_people)
                .crossFade()
                .into(holder.ivProfilePic);

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
                tvName.setText(mItems.get(position).getPostedBy());
                Glide.with(mContext)
                        .load(mItems.get(position).getProfilePic())
                        .centerCrop()
                        .placeholder(R.drawable.ic_people)
                        .crossFade()
                        .into(imageView);

                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
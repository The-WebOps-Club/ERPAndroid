package org.saarang.erp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        public ViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvInfo = (TextView) view.findViewById(R.id.tvInfo);
            tvWall = (TextView) view.findViewById(R.id.tvWall);
        }
    }

    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(mItems.get(position).getPostedBy());
        holder.tvTitle.setText(mItems.get(position).getTitle());
        holder.tvInfo.setText(mItems.get(position).getInfo());
        holder.tvWall.setText(mItems.get(position).getWall());

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
package org.saarang.erp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.saarang.erp.Activities.SinglePostActivity;
import org.saarang.saarangsdk.Helpers.TimeHelper;
import org.saarang.erp.Objects.ERPPostTemp;
import org.saarang.erp.R;

import java.util.ArrayList;

/**
 * Created by Ahammad on 22/06/15.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    Context mContext;
    ArrayList<ERPPostTemp> mItems;




    public NotificationsAdapter(Context context,ArrayList<ERPPostTemp> items){
        mContext=context;
        mItems=items;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNotification1,tvNotification2;
        ImageView ivNotification;
        LinearLayout llNotifications;
        Intent intent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNotification1=(TextView) itemView.findViewById(R.id.tvNotification1);
            tvNotification2=(TextView) itemView.findViewById(R.id.tvNotification2);
            ivNotification=(ImageView)itemView.findViewById(R.id.ivNotification);
            llNotifications = (LinearLayout)itemView.findViewById(R.id.llItemNotification);
            intent = new Intent(mContext, SinglePostActivity.class);
        }
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationsAdapter.ViewHolder holder, int position) {
        holder.tvNotification1.setText(mItems.get(position).getPostedBy() + " " + mItems.get(position).getAction()
                + "\nOn " + mItems.get(position).getWall() + " Wall");

        Glide.with(mContext)
                .load(mItems.get(position).getProfilePic())
                .centerCrop()
                .placeholder(R.drawable.ic_people)
                .crossFade()
                .skipMemoryCache(false)
                .into(holder.ivNotification);

        TimeHelper th=new TimeHelper();
        String testTime="2015-07-02T09:31:35.333Z";

        holder.tvNotification2.setText(th.getRelative(testTime));

        holder.llNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(holder.intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}

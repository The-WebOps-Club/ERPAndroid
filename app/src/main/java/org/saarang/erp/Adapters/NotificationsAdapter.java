package org.saarang.erp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.saarang.erp.Activities.SinglePostActivity;
import org.saarang.erp.Objects.ERPNotification;
import org.saarang.erp.R;
import org.saarang.erp.Utils.URLConstants;
import org.saarang.saarangsdk.Helpers.TimeHelper;

import java.util.List;

/**
 * Created by Ahammad on 22/06/15.
 */
public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    Context mContext;
    List<ERPNotification> mItems;
    String notification_text, postId;

    public NotificationsAdapter(Context context, List<ERPNotification> items){
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
        }
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationsAdapter.ViewHolder holder, final int position) {

        postId = mItems.get(position).getPostId();

        // using html for setting color only for the name of posted person.
        notification_text = "<font color = '#3F51B5'> " +mItems.get(position).getUser()[0].getName() +" </font>"+ " " +
                mItems.get(position).getAction()
                + "ed on " + mItems.get(position).getWall().getName() + "'s wall";

        holder.tvNotification1.setText(Html.fromHtml(notification_text));

        final String profilePicUrl = URLConstants.SERVER + "api/users/" + mItems.get(position).getUser()[0].get_id() + "/profilePic";
        Glide.with(mContext)
                .load(profilePicUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_people)
                .crossFade()
                .into(holder.ivNotification);

        TimeHelper th=new TimeHelper();
        String testTime="2015-07-02T09:31:35.333Z";

        holder.tvNotification2.setText(th.getRelative(testTime));

        holder.llNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SinglePostActivity.class);
                intent.putExtra(SinglePostActivity.EXTRA_POSTID, postId );
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


}

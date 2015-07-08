package org.saarang.erp.Adapters;

/**
 * Created by Ajmal on 23-06-2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.saarang.erp.Objects.ERPComment;
import org.saarang.erp.R;
import org.saarang.saarangsdk.Helpers.TimeHelper;

import java.util.List;


public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    Context mContext;
    List<ERPComment> items;
    TimeHelper th=new TimeHelper();
    private String LOG_TAG = "Comments Adapter";

    public CommentsAdapter(Context context,  List<ERPComment> items){
        mContext=context;
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProfilePic;
        TextView tvProfileName, tvComment, tvTime;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfilePic =(ImageView)itemView.findViewById(R.id.ivComment);
            tvProfileName =(TextView)itemView.findViewById(R.id.tvComment1);
            tvComment =(TextView)itemView.findViewById(R.id.tvComment2);
            tvTime =(TextView)itemView.findViewById(R.id.tvComment3);
        }
    }
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvComment.setText(items.get(position).getInfo());
        holder.tvProfileName.setText(items.get(position).getCreatedBy().getName());
        holder.tvTime.setText(th.getRelative(items.get(position).getCreatedOn()));
        Log.d(LOG_TAG,th.getRelative(items.get(position).getCreatedOn()) + items.get(position).getInfo() );
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}


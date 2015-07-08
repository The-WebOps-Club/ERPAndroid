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

import org.saarang.erp.Objects.ERPAcknowledged;
import org.saarang.erp.R;

import java.util.List;


public class CommentsSecondAdapter extends RecyclerView.Adapter<CommentsSecondAdapter.ViewHolder> {

    Context mContext;
    List<ERPAcknowledged> items;

    public CommentsSecondAdapter(Context context ,List<ERPAcknowledged> items){
        mContext=context;
        this.items = items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAcknoUser;
        TextView tvAcknoName;

        public ViewHolder(View itemView) {
            super(itemView);

            ivAcknoUser=(ImageView)itemView.findViewById(R.id.ivAcknoUser);
            tvAcknoName=(TextView)itemView.findViewById(R.id.tvAcknoName);
        }
    }
    @Override
    public CommentsSecondAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ackno, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsSecondAdapter.ViewHolder holder, int position) {
        // Setting name
        holder.tvAcknoName.setText(items.get(position).getCreatedBy().getName());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}


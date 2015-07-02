package org.saarang.erp.Adapters;

/**
 * Created by Ajmal on 23-06-2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.saarang.erp.R;


public class CommentsSecondAdapter extends RecyclerView.Adapter<CommentsSecondAdapter.ViewHolder> {

    Context mContext;


    public CommentsSecondAdapter(Context context){
        mContext=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPic;
        TextView tvCommentName,tvCommentComment,tvCommentTime;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPic=(ImageView)itemView.findViewById(R.id.ivPic);
            tvCommentName=(TextView)itemView.findViewById(R.id.tvComment1);


        }
    }
    @Override
    public CommentsSecondAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ackno, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsSecondAdapter.ViewHolder holder, int position) {
    }


    @Override
    public int getItemCount() {
        return 16;
    }

}


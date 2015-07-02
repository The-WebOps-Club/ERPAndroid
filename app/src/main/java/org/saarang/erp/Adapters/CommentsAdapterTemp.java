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

import org.saarang.erp.Helper.TimeHelper;
import org.saarang.erp.R;


public class CommentsAdapterTemp extends RecyclerView.Adapter<CommentsAdapterTemp.ViewHolder> {

    Context mContext;


    public CommentsAdapterTemp(Context context){
        mContext=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivComment;
        TextView tvCommentName,tvCommentComment,tvCommentTime;

        public ViewHolder(View itemView) {
            super(itemView);

            ivComment=(ImageView)itemView.findViewById(R.id.ivComment);
            tvCommentName=(TextView)itemView.findViewById(R.id.tvComment1);
            tvCommentComment=(TextView)itemView.findViewById(R.id.tvComment2);
            tvCommentTime=(TextView)itemView.findViewById(R.id.tvComment3);
        }
    }
    @Override
    public CommentsAdapterTemp.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsAdapterTemp.ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 16;
    }


}


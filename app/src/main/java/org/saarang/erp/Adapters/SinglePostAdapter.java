package org.saarang.erp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.saarang.erp.R;

import java.util.ArrayList;

/**
 * Created by Seetharaman on 04-07-2015.
 */
public class SinglePostAdapter extends RecyclerView.Adapter<SinglePostAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Integer> viewTypes;

    public SinglePostAdapter(Context context){
        mContext = context;
        viewTypes = new ArrayList<>(2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view ;

        switch (viewType){
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_feed, parent, false);
                break;
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
                break;
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if(position == 0)
        {
            //give post details here
        }
        else
        {
            //give comments here
        }
    }



    @Override
    public int getItemCount() {
        return 2;
    }

}

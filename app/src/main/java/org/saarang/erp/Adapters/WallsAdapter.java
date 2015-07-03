package org.saarang.erp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.saarang.erp.Activities.MainActivity;
import org.saarang.erp.Activities.WallActivity;
import org.saarang.erp.Objects.ERPWall;
import org.saarang.erp.R;

import java.util.ArrayList;

/**
 * Created by Seetharaman on 28-06-2015.
 */
public class WallsAdapter extends RecyclerView.Adapter<WallsAdapter.ViewHolder> {

    Context mContext;
    ArrayList<ERPWall> mItems;

    public WallsAdapter(Context context, ArrayList<ERPWall> departments) {
        mContext = context;
        mItems = departments;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDepartment;
        CardView card_view;

        public ViewHolder(View view) {
            super(view);
            tvDepartment = (TextView)view.findViewById(R.id.tvDepartment);
            card_view = (CardView)view.findViewById(R.id.card_view);
        }
    }

    @Override
    public WallsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_wall, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvDepartment.setText(mItems.get(position).getName());
        holder.tvDepartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((MainActivity)mContext, WallActivity.class);
            }
        });
        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, WallActivity.class);
                intent.putExtra(WallActivity.EXTRA_WALLID, mItems.get(position).get_id());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}

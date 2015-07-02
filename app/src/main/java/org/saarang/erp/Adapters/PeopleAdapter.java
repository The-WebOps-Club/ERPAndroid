package org.saarang.erp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.saarang.erp.R;

/**
 * Created by Seetharaman on 28-06-2015.
 */
public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    Context mContext;
    String[] mItems;

    public PeopleAdapter(Context context, String[] departements) {
        mContext = context;
        mItems = departements;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvDepartment;


        public ViewHolder(View view) {
            super(view);
            tvDepartment = (TextView)view.findViewById(R.id.tvDepartment);
        }
    }

    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_people, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvDepartment.setText(mItems[position]);
    }

    @Override
    public int getItemCount() {
        return mItems.length;
    }
}

package org.saarang.erp.Adapters;

/**
 * Created by Moochi on 23-06-2015.
 */

import android.support.v7.widget.RecyclerView;


        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;

        import org.saarang.erp.R;


public class WallsAdapter extends RecyclerView.Adapter<WallsAdapter.ViewHolder> {

    @Override
    public WallsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_walls, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WallsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}


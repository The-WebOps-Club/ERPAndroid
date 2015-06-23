package org.saarang.erp.Adapters;

/**
 * Created by Moochi on 23-06-2015.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;


        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
import android.widget.Button;

import org.saarang.erp.R;



public class WallsAdapter extends RecyclerView.Adapter<WallsAdapter.ViewHolder> {

    String[] departments={
            "Finance",
            "Publicity",
            "Design and Media",
            "Marketing and Sales",
            "Finance",
            "Web and Mobile Operations"
    };

    Context mContext;


    public WallsAdapter(Context context){
        mContext=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btWall;


        public ViewHolder(View itemView) {
            super(itemView);

            btWall=(Button)itemView.findViewById(R.id.btWall);
        }
    }
    @Override
    public WallsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_walls, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WallsAdapter.ViewHolder holder, int position) {
            holder.btWall.setText(departments[position]);
    }

    @Override
    public int getItemCount() {
        return 6;
    }


}


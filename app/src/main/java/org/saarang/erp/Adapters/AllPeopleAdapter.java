package org.saarang.erp.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by kevin selva prasanna on 04-Jul-15.
 */

public class AllPeopleAdapter extends RecyclerView.Adapter<AllPeopleAdapter.ViewHolder>{

    Context mContext;

    public AllPeopleAdapter(Context context){
        mContext=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView pic;
        TextView name;

        public ViewHolder(View itemView){
            super(itemView);

            pic= (CircleImageView)itemView.findViewById(R.id.people_pic);
            name = (TextView)itemView.findViewById(R.id.name);
        }
    }


    @Override
    public AllPeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allpeople, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllPeopleAdapter.ViewHolder holder, int position) {
        holder.pic.setImageResource(R.drawable.bg_ajmal);
        //holder.name.setText(position);

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}


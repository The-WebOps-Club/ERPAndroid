package org.saarang.erp.Adapters;

/**
 * Created by Ajmal on 23-06-2015.
 */

        import android.content.Context;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;


        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.TextView;

        import org.saarang.erp.R;
        import org.w3c.dom.Text;


public class WallsAdapter extends RecyclerView.Adapter<WallsAdapter.ViewHolder> {

    String[] departments={
            "Finance",
            "Publicity",
            "Design and Media",
            "Marketing and Sales",
            "Mobile Operations",
            "Web Operations"
    };

    int[] people={
            12,12,12,12,112,33
    };

    Context mContext;


    public WallsAdapter(Context context){
        mContext=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivWall;
        TextView tvWallName, tvWallPeople;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            ivWall=(ImageView)itemView.findViewById(R.id.ivWall);
            tvWallName=(TextView)itemView.findViewById(R.id.tvWall1);
            tvWallPeople=(TextView)itemView.findViewById(R.id.tvWall2);

            /*cardView = (CardView) itemView.findViewById(R.id.cvWall);
            cardView.setCardElevation(15f);
            cardView.setRadius(36f);*/
        }
    }
    @Override
    public WallsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_walls, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WallsAdapter.ViewHolder holder, int position) {
        holder.tvWallName.setText(departments[position]);
        holder.tvWallPeople.setText(people[position]+" Members");

    }

    @Override
    public int getItemCount() {
        return 6;
    }


}


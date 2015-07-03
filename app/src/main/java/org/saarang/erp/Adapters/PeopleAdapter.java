package org.saarang.erp.Adapters;

/**
 * Created by Ajmal on 23-06-2015.
 */

        import android.content.Context;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;


        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import org.saarang.erp.R;


public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

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


    public PeopleAdapter(Context context){
        mContext=context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPeople;
        TextView tvPeopleName, tvPeoplePeople;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPeople=(ImageView)itemView.findViewById(R.id.ivPeople);
            tvPeopleName=(TextView)itemView.findViewById(R.id.tvPeople1);
            tvPeoplePeople=(TextView)itemView.findViewById(R.id.tvPeople2);

            /*cardView = (CardView) itemView.findViewById(R.id.cvPeople);
            cardView.setCardElevation(15f);
            cardView.setRadius(36f);*/
        }
    }
    @Override
    public PeopleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_people, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PeopleAdapter.ViewHolder holder, int position) {
        holder.tvPeopleName.setText(departments[position]);
        holder.tvPeoplePeople.setText(people[position]+" Members");

    }

    @Override
    public int getItemCount() {
        return 6;
    }


}


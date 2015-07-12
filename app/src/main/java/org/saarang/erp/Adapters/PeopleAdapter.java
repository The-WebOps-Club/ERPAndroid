package org.saarang.erp.Adapters;

/**
 * Created by Ajmal on 23-06-2015.
 */

        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.CardView;
        import android.support.v7.widget.RecyclerView;


        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ImageView;
        import android.widget.TextView;

        import org.saarang.erp.Activities.MainActivity;
        import org.saarang.erp.Activities.PeopleActivity;
        import org.saarang.erp.Activities.WallActivity;
        import org.saarang.erp.R;
        import org.saarang.erp.Utils.AppConstants;


public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    String[] departments= AppConstants.departments;


    int[] people={
            12,12,12,12,112,33,11,12,1,3,12,12
    };

    Context mContext;


    public PeopleAdapter(Context context){
        mContext=context;
    }

  /*  @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(mContext,PeopleActivity.class);
        mContext.startActivity(i);


    }*/

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPeople;
        TextView tvPeopleName, tvPeoplePeople;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            ivPeople=(ImageView)itemView.findViewById(R.id.ivPeople);
            tvPeopleName=(TextView)itemView.findViewById(R.id.tvPeople1);
            tvPeoplePeople=(TextView)itemView.findViewById(R.id.tvPeople2);

            cardView = (CardView) itemView.findViewById(R.id.cvPeople);
            /*cardView.setCardElevation(15f);
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
        /*holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, PeopleActivity.class);
                mContext.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return 6;
    }


}


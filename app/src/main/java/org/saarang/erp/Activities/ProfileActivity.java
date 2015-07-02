package org.saarang.erp.Activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.saarang.erp.Objects.ERPPostTemp;
import org.saarang.erp.R;
import org.saarang.erp.Utils.RandomGenerator;

import java.util.ArrayList;


public class ProfileActivity extends ActionBarActivity {

    int position;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<ERPPostTemp> arrayList = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);

        Intent profile = getIntent();
        position = profile.getIntExtra("pos", 5);


        CollapsingToolbarLayout collapsing_toolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsing_toolbar);
        collapsing_toolbar.setTitle("Ajmal Moochingal");

        TextView tvName = (TextView)findViewById(R.id.tvName);
//        final TextView tvPrimNum = (TextView)findViewById(R.id.tvPrimNum);
//        final TextView tvSecNum = (TextView)findViewById(R.id.tvSecNum);
//        final TextView tvEmail = (TextView)findViewById(R.id.tvEmail);
//        ImageView ivProfilePic = (ImageView)findViewById(R.id.ivProfilePic);
//        ImageButton ibCallPrim = (ImageButton)findViewById(R.id.ibCallPrim);
//        ImageButton ibCallSec = (ImageButton)findViewById(R.id.ibCallSec);
//        ImageButton ibEmail = (ImageButton)findViewById(R.id.ibEmail);
//
//        ibCallPrim.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Intent callNumber = new Intent();
//                        callNumber.setAction(android.content.Intent.ACTION_DIAL);
//                        callNumber.setData(Uri.parse("tel:" + tvPrimNum.getText().toString()));
//                        startActivity(callNumber);
//                    }
//                }
//        );

//        ibCallSec.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        Intent callNumber = new Intent();
//                        callNumber.setAction(android.content.Intent.ACTION_DIAL);
//                        callNumber.setData(Uri.parse("tel:" + tvSecNum.getText().toString()));
//                        startActivity(callNumber);
//                    }
//                }
//        );
//
//        ibEmail.setOnClickListener(
//                new View.OnClickListener() {
//
//                    @Override
//                    public void onClick(View v) {
//
//                        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
//                        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, tvEmail.getText().toString());
//                        emailIntent.setType("plain/text");
//                        startActivity(Intent.createChooser(emailIntent, "Send your email in:"));
//                    }
//                }
//        );

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Random dataset
        arrayList = RandomGenerator.getRandomPosts(15);

//        adapter = new NewsFeedAdapter(this, arrayList);
//        recyclerView.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ac_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

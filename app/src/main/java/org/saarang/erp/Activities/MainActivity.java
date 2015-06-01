package org.saarang.erp.Activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.saarang.erp.Adapters.ViewPagerAdapter;
import org.saarang.erp.Fragments.NewsFeedFragment;
import org.saarang.erp.R;

public class MainActivity extends ActionBarActivity implements View.OnClickListener{

    ImageView[] imageViews;
    ViewPager viewPager;
    int[] drawableSelected, drawableDefault;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new NewsFeedFragment())
                    .commit();
        }
        imageViews = new ImageView[]{ (ImageView)findViewById(R.id.iv1), (ImageView)findViewById(R.id.iv2), (ImageView)findViewById(R.id.iv3), (ImageView)findViewById(R.id.iv4) };
        for (int i=0; i<imageViews.length; i++){
            imageViews[i].setOnClickListener(this);
        }
        drawableDefault = new int[]{R.drawable.ic_newsfeed, R.drawable.ic_notifications , R.drawable.ic_wall,  R.drawable.ic_people};
        drawableSelected = new int[]{R.drawable.ic_newsfeed_selected, R.drawable.ic_notifications_selected , R.drawable.ic_wall_selected,  R.drawable.ic_people_selected};
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {
                changeIcons(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void changeIcons(int position) {
        for (int i = 0; i<4; i++){
            imageViews[i].setImageResource(drawableDefault[i]);
        }
        imageViews[position].setImageResource(drawableSelected[position]);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ac_main, menu);
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


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv1:
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, new NewsFeedFragment())
                        .commit();
                break;
        }
    }
}
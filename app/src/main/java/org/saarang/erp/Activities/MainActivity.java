/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.saarang.erp.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.saarang.erp.Fragments.NewsFeedFragment;
import org.saarang.erp.Fragments.NotificationsFragment;
import org.saarang.erp.Fragments.PeopleFragment;
import org.saarang.erp.Fragments.WallsFragment;
import org.saarang.erp.Objects.ERPProfile;
import org.saarang.erp.R;
import org.saarang.erp.Services.RegistrationIntentService;
import org.saarang.erp.Utils.UserState;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * TODO
 */
public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    CircleImageView pro_pic;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }

        //Read last user state and redirect
        Intent intent;
        switch (UserState.getLastActivity(MainActivity.this)){
            case 1:
                intent= new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case 2:
                intent= new Intent(MainActivity.this,ProfilePictureActivity.class);
                startActivity(intent);
                finish();
                break;
            case 3:
                intent= new Intent(MainActivity.this,UpdateProfileActivity.class);
                startActivity(intent);
                finish();
                break;
            case 4:
                break;
        }

        setContentView(R.layout.ac_main);
        username = (TextView)findViewById(R.id.user_name);
        username.setText(ERPProfile.getERPUserName(MainActivity.this));
        pro_pic = (CircleImageView)findViewById(R.id.profile_image);
        Log.d("pro_id", ERPProfile.getUserProfilePicId(MainActivity.this));
        pro_pic.setImageURI(Uri.fromFile(new File(ERPProfile.getUserProfilePic (MainActivity.this))));
        /**
         * Action Bar
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        if (viewPager != null) {
            setupViewPager(viewPager);
        }

        final Intent intentPost = new Intent(this, NewPostActivity.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3F51B5")));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Pained", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                startActivity(intentPost);
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i("tag", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(new NewsFeedFragment(), "News Feed");
        adapter.addFragment(new NotificationsFragment(), "Notifications");
        adapter.addFragment(new WallsFragment(), "Walls");
        adapter.addFragment(new PeopleFragment(), "People");
        viewPager.setAdapter(adapter);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void logout(MenuItem item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("SaarangERP");
        builder.setMessage("Are you sure you want to logout?");
        //Creating ok button with listener
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("Alert", " Ok");
                        clearApplicationData();
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        prefs.edit().putString("delete", "hellothisisacheck").apply();
                        Log.d("delete", prefs.getString("delete", "nope"));
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.commit();
                        Log.d("delete", prefs.getString("delete", "nope"));
                        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(getBaseContext());
                        try {
                            gcm.unregister();
                        } catch (IOException e) {
                            System.out.println("Error Message: " + e.getMessage());
                        }
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();

                    }

                    public void clearApplicationData() {
                        File cache = getCacheDir();
                        File appDir = new File(cache.getParent());
                        if (appDir.exists()) {
                            String[] children = appDir.list();
                            for (String s : children) {
                                if (!s.equals("lib")) {
                                    deleteDir(new File(appDir, s));
                                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s + " DELETED");
                                }
                            }
                        }
                    }

                    public static boolean deleteDir(File dir) {
                        if (dir != null && dir.isDirectory()) {
                            String[] children = dir.list();
                            for (int i = 0; i < children.length; i++) {
                                boolean success = deleteDir(new File(dir, children[i]));
                                if (!success) {
                                    return false;
                                }
                            }
                        }

                        return dir.delete();
                    }

                    static class Adapter extends FragmentPagerAdapter {
                        private final List<Fragment> mFragments = new ArrayList<>();
                        private final List<String> mFragmentTitles = new ArrayList<>();

                        public Adapter(FragmentManager fm) {
                            super(fm);
                        }

                        public void addFragment(Fragment fragment, String title) {
                            mFragments.add(fragment);
                            mFragmentTitles.add(title);
                        }

                        @Override
                        public Fragment getItem(int position) {
                            return mFragments.get(position);
                        }

                        @Override
                        public int getCount() {
                            return mFragments.size();
                        }

                        @Override
                        public CharSequence getPageTitle(int position) {
                            return mFragmentTitles.get(position);
                        }
                    }
                }

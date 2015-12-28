package com.tossapon.stadiumfinder.GroupActivity.MainActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Adapter.ReserveAdapter;
import com.tossapon.stadiumfinder.Api.MainInterface;
import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.AppModel.AllStadiumResponse;
import com.tossapon.stadiumfinder.AppModel.Response;
import com.tossapon.stadiumfinder.GroupActivity.MainActivity.Fragment.PlayNowFragment;
import com.tossapon.stadiumfinder.GroupActivity.MainActivity.Fragment.PlayWithFriendFragment;
import com.tossapon.stadiumfinder.GroupActivity.MainActivity.Fragment.ReserveFragment;
import com.tossapon.stadiumfinder.GroupActivity.MainActivity.Fragment.WhatNewFragment;
import com.tossapon.stadiumfinder.Model.Reserve;
import com.tossapon.stadiumfinder.Network.Server;
import com.tossapon.stadiumfinder.Util.DataLoader;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    final String[] types = {"ฟุตบอล", "ฟุตซอล", "เทนนิส", "บาสเก็ตบอล", "เทเบิล เทนนิส", "แบดมินตัน"};

    int currentSport = 0;
    String currentSportAsString = "soccer";
    int currentMode = R.id.nav_now;

    @Bind(R.id.activity_main_toolbar)
    Toolbar toolbar;

    @Bind(R.id.activity_main_drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.activity_main_nav_view)
    NavigationView navigationView;

    @Bind(R.id.activity_main_sport_type)
    TextView sportType;

    CircleImageView circleImageView;
    TextView name;

    @Bind(R.id.activity_main_sport_bg)
    ImageView sportBg;

    @Bind(R.id.activity_main_collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.activity_main_recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        circleImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.activity_main_circle_image_view);
        name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.activity_main_text_view_name);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
//        navigationView.addHeaderView();

        Picasso.with(getApplicationContext()).load(AppUser.getInstance().picurl).into(circleImageView);
        name.setText(AppUser.getInstance().name);
    }

    public void onSportChangeListerner(int which, View v){
        int id = 0;
        switch (which){
            case 0 :
                id = R.drawable.soccer;
                currentSportAsString = "soccer";
                break;
            case 1 :
                id = R.drawable.futsol;
                currentSportAsString = "futsal";
                break;
            case 2 :
                id = R.drawable.tennis;
                currentSportAsString = "tennis";
                break;
            case 3 :
                id = R.drawable.basketball;
                currentSportAsString = "basketball";
                break;
            case 4 :
                id = R.drawable.tt;
                currentSportAsString = "pingpong";
                break;
            case 5 :
                id = R.drawable.badminton;
                currentSportAsString = "badminton";
                break;
        }
        sportBg.setImageResource(id);
        currentSport = which;
        changePageFragmentAndData();
    }

    @OnClick(R.id.activity_main_sport_type)
    public void onClickSportType(View v){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("เลือกกีฬา");
        b.setItems(types, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sportType.setText(types[which]);
                onSportChangeListerner(which, sportType);
            }
        });
        b.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activity_main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_now:
            case R.id.nav_reserve:
            case R.id.nav_play:
            case R.id.nav_quick:
                currentMode = id;
                changePageFragmentAndData();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changePageFragmentAndData(){
        Fragment f = null;
        Response res = null;

        ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Loading", "กำลังรวบรวมข้อมูล");
        Retrofit client = new Retrofit.Builder()
                .baseUrl(Server.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MainInterface service = client.create(MainInterface.class);


        switch (currentMode){
            case R.id.nav_now:
//                f = WhatNewFragment.newInstance(currentSport, );
                break;
            case R.id.nav_reserve:
                collapsingToolbarLayout.setTitle("จองสนาม");
                final Call<AllStadiumResponse> call = service.getStadium(AppUser.getInstance().facebook_id, currentSportAsString);
                AllStadiumResponse allStadiumResponse = null;

                try {
                    allStadiumResponse = new AsyncTask<Void, Void , AllStadiumResponse>(){
                        @Override
                        protected AllStadiumResponse doInBackground(Void... params) {
                            try {
                                return call.execute().body();
                            } catch (IOException e) {
                                return null;
                            }
                        }
                    }.execute().get();
                } catch (InterruptedException e) {
                    Snackbar.make(drawer, "Error :" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                } catch (ExecutionException e) {
                    Snackbar.make(drawer, "Error :" + e.getMessage(), Snackbar.LENGTH_LONG).show();
                }

                if(allStadiumResponse == null){
                    Snackbar.make(drawer, "Error :" + "Something Error", Snackbar.LENGTH_LONG).show();
                    return;
                }

                mRecyclerView.setNestedScrollingEnabled(false);
                mRecyclerView.setHasFixedSize(false);

                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new ReserveAdapter(allStadiumResponse.data);
                mRecyclerView.setAdapter(mAdapter);
                break;
            case R.id.nav_play:
//                f = PlayWithFriendFragment.newInstance(currentSport);
                break;
            case R.id.nav_quick:
//                f = PlayNowFragment.newInstance(currentSport);
                break;
        }

        progressDialog.dismiss();

    }
}

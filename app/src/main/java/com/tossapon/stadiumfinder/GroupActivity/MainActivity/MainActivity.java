package com.tossapon.stadiumfinder.GroupActivity.MainActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Adapter.FriendMatchAdapter;
import com.tossapon.stadiumfinder.Adapter.QuickmatchAdapter;
import com.tossapon.stadiumfinder.Adapter.ReserveAdapter;
import com.tossapon.stadiumfinder.Api.MainInterface;
import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.App.LatLngModule;
import com.tossapon.stadiumfinder.GroupActivity.FriendActivity.FriendActivity;
import com.tossapon.stadiumfinder.GroupActivity.MyReserveActivity.MyReserveActivity;
import com.tossapon.stadiumfinder.GroupActivity.SettingsActivity.SettingsActivity;
import com.tossapon.stadiumfinder.GroupActivity.Splash.Splash;
import com.tossapon.stadiumfinder.Model.Response.AllFriendMatchResponse;
import com.tossapon.stadiumfinder.Model.Response.AllQuickMatchResponse;
import com.tossapon.stadiumfinder.Model.Response.AllStadiumResponse;
import com.tossapon.stadiumfinder.Network.Server;
import com.tossapon.stadiumfinder.Util.ExpansiveLayoutManager;
import com.tossapon.stadiumfinder.Util.FileUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    final String[] typesTh = {"ฟุตบอล", "ฟุตซอล", "เทนนิส", "บาสเก็ตบอล", "เทเบิล เทนนิส", "แบดมินตัน"};
    final String[] typesEng = {"soccer", "futsal", "tennis", "basketball", "pingpong", "badminton"};
    String currentSportAsString = "soccer";
    int currentMode = R.id.nav_playfriend;

    @Bind(R.id.activity_main_toolbar)
    Toolbar toolbar;

    @Bind(R.id.activity_main_drawer_layout)
    DrawerLayout drawer;

    @Bind(R.id.activity_main_nav_view)
    NavigationView navigationView;

    CircleImageView circleImageView;
    TextView name;

    @Bind(R.id.activity_main_tab)
    TabLayout tabLayout;

//    @Bind(R.id.activity_main_collapse_toolbar)
//    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.activity_main_recycler_view)
    RecyclerView mRecyclerView;

    @Bind(R.id.activity_main_text)
    TextView textStatus;

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    private boolean debug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        for (int i = 0; i < typesTh.length; i++)
            tabLayout.addTab(tabLayout.newTab().setText(typesTh[i]));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentSportAsString = typesEng[tab.getPosition()];
                changePageFragmentAndData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        circleImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.activity_main_circle_image_view);
        name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.activity_main_text_view_name);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        Picasso.with(getApplicationContext()).load(AppUser.getInstance().getPicurl()).into(circleImageView);
        name.setText(AppUser.getInstance().getName());
        changePageFragmentAndData();

//        LatLngModule.newInstance(7.9030052, 98.3471783);
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
        Intent i = null;
        switch (id){
            case R.id.nav_playfriend:
            case R.id.nav_reserve:
            case R.id.nav_quick:
                currentMode = id;
                changePageFragmentAndData();
                break;
            case R.id.nav_my:
                i = new Intent(this, MyReserveActivity.class);
                startActivity(i);
                break;
            case R.id.nav_friend:
                i = new Intent(this, FriendActivity.class);
                startActivity(i);
                break;
            case R.id.nav_logout:
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("ออกจากระบบ")
                        .setMessage("คุณต้องการออกจากระบบหรือไม่ ?")
                        .setPositiveButton("ใช่", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                LoginManager.getInstance().logOut();
                                Intent i = new Intent(MainActivity.this, Splash.class);
                                startActivity(i);
                                finish();
                            }
                        }).setNegativeButton("ให้ฉันอยู่ต่อ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.nav_setting:
                i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changePageFragmentAndData(){
        ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "Loading", "กำลังรวบรวมข้อมูล");
        Retrofit client = new Retrofit.Builder()
                .baseUrl(Server.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final MainInterface service = client.create(MainInterface.class);

        final ProgressDialog dialog;
        switch (currentMode){
            //จองสนาม
            case R.id.nav_reserve:
//                collapsingToolbarLayout.setTitle("จองสนาม");
                getSupportActionBar().setTitle("จองสนาม");
                dialog = ProgressDialog.show(MainActivity.this, "", "กำลังโหลดข้อมูล", true);
                Call<AllStadiumResponse> call = service.getStadium(AppUser.getInstance().getFacebook_id(),
                        currentSportAsString,
                        LatLngModule.getInstance().latitude,
                        LatLngModule.getInstance().longitude);
                call.enqueue(new Callback<AllStadiumResponse>() {
                    @Override
                    public void onResponse(retrofit.Response<AllStadiumResponse> response, Retrofit retrofit) {
                        mRecyclerView.setNestedScrollingEnabled(false);
                        mRecyclerView.setHasFixedSize(false);
                        mLayoutManager = new ExpansiveLayoutManager(MainActivity.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        if(response.body().data.size() == 0)
                            textStatus.setText("ไม่มีข้อมูล");
                        else
                            textStatus.setText("");
                        mAdapter = new ReserveAdapter(response.body().data, currentSportAsString);
                        mRecyclerView.setAdapter(mAdapter);
                        if (debug)
                            Log.d(TAG, "onResponse: จองสนาม " + mAdapter.getItemCount() + " item");
                        dialog.dismiss();
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Snackbar.make(drawer, "Error : จองสนาม" + t.getMessage(), Snackbar.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                break;
            //เล่นกับเพื่อน
            case R.id.nav_playfriend:
                getSupportActionBar().setTitle("เล่นกับเพื่อน");
                dialog = ProgressDialog.show(MainActivity.this, "", "กำลังโหลดข้อมูล", true);
                GraphRequest request = GraphRequest.newMyFriendsRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray objects, GraphResponse response) {
                        if(debug)
                            Log.d(TAG, "onResponse: เล่นกับเพื่อน" + objects.toString());

                        try {
                            File f = new File(getFilesDir().getPath() + "/blockFriend");
                            if(!f.exists())
                                FileUtil.writeFile(getApplicationContext(), "blockFriend", "[]");
                            String friendsBlocked = FileUtil.readFile(getApplicationContext(), "blockFriend");
                            for(int i = 0 ; i < objects.length() ; i++){
                                if(friendsBlocked.contains(objects.getJSONObject(i).getString("id"))){
                                    Log.d(TAG, "onCompleted: remove friend" + objects.getJSONObject(i).getString("id"));
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                                        objects.remove(i);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d(TAG, "onCompleted: " + objects.toString());

                        Call<AllFriendMatchResponse> friendCall = service.getFriendMatch(
                                objects.toString(),
                                AppUser.getInstance().getFacebook_id(),
                                currentSportAsString,
                                LatLngModule.getInstance().latitude,
                                LatLngModule.getInstance().longitude);
                        friendCall.enqueue(new Callback<AllFriendMatchResponse>() {
                            @Override
                            public void onResponse(retrofit.Response<AllFriendMatchResponse> res, Retrofit retrofit) {
                                mRecyclerView.setHasFixedSize(false);
                                mRecyclerView.setNestedScrollingEnabled(false);
                                mLayoutManager = new LinearLayoutManager(MainActivity.this);
                                if(res.body().getData().size() == 0)
                                    textStatus.setText("ยังไม่มีใครจองสนามเลย \nคุณลองจองสนามและชวนเพื่อนๆของคุณ");
                                else
                                    textStatus.setText("");
                                mRecyclerView.setLayoutManager(mLayoutManager);
                                mAdapter = new FriendMatchAdapter(res.body().getData());
                                mRecyclerView.setAdapter(mAdapter);
                                if(debug)
                                    Log.d(TAG, "onResponse: เล่นกับเพื่อน" + res.body().getStatus());
                                dialog.dismiss();
                            }
                            @Override
                            public void onFailure(Throwable t) {
                                Snackbar.make(drawer, "Error : เล่นกับเพื่อน " + t.getMessage(), Snackbar.LENGTH_LONG).show();
                                Log.d(TAG, "onFailure: " + t.getMessage());
                                dialog.dismiss();
                            }
                        });
                    }
                });
                request.executeAsync();
                break;

            //เล่นตอนนี้
            case R.id.nav_quick:
                getSupportActionBar().setTitle("เล่นตอนนี้");
                dialog = ProgressDialog.show(MainActivity.this, "", "กำลังโหลดข้อมูล", true);
                Call<AllQuickMatchResponse> callQuickMatch = service.getQuickMatch(
                        LatLngModule.getInstance().latitude,
                        LatLngModule.getInstance().longitude,
                        currentSportAsString,
                        AppUser.getInstance().getFacebook_id());
                callQuickMatch.enqueue(new Callback<AllQuickMatchResponse>() {
                    @Override
                    public void onResponse(retrofit.Response<AllQuickMatchResponse> response, Retrofit retrofit) {
                        mRecyclerView.setNestedScrollingEnabled(false);
                        mRecyclerView.setHasFixedSize(false);
                        Log.d(TAG, "onResponse: เล่นตอนนี้ " + response.message() + "\ncode:" + response.code()) ;
                        if(response.body().getData().size() == 0)
                            textStatus.setText("ยังไม่มีใครจองสนามเลย \nคุณลองจองสนามและรอเพื่อนๆ จอยกับคุณ");
                        else
                            textStatus.setText("");
                        mLayoutManager = new ExpansiveLayoutManager(MainActivity.this);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mAdapter = new QuickmatchAdapter(response.body().getData());
                        mRecyclerView.setAdapter(mAdapter);
                        if(debug)
                            Log.d(TAG, "onResponse: เล่นตอนนี้ " + response.message());
                        dialog.dismiss();
                    }
                    @Override
                    public void onFailure(Throwable t) {
                        Snackbar.make(drawer, "Error : เล่นตอนนี้ " + t.getMessage(), Snackbar.LENGTH_LONG).show();
                        dialog.dismiss();
                    }
                });
                break;
        }

        progressDialog.dismiss();
    }
}

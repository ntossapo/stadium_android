package com.tossapon.stadiumfinder.GroupActivity.MatchDetailActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Adapter.FriendMiniAdapter;
import com.tossapon.stadiumfinder.Api.QuickInterface;
import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.Model.Advance.FriendMatch;
import com.tossapon.stadiumfinder.Model.Advance.QuickMatch;
import com.tossapon.stadiumfinder.Model.Response.Response;
import com.tossapon.stadiumfinder.Network.Server;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MatchDetailActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final String TAG = "MatchDetailActivity";
    QuickMatch quickMatch;
    FriendMatch friendMatch;

    @Bind(R.id.activity_match_detail_toolbar)
    Toolbar toolbar;
    @Bind(R.id.activity_match_detail_stadium_image)
    ImageView stadiumImage;
    @Bind(R.id.activity_match_detail_stadium_name)
    TextView stadiumName;
    @Bind(R.id.activity_match_detail_date)
    TextView dateTextView;
    @Bind(R.id.activity_match_detail_time)
    TextView timeTextView;
    @Bind(R.id.activity_match_detail_noplayer)
    TextView noplayer;
    @Bind(R.id.activity_match_detail_reserver)
    TextView reserverTextView;
    @Bind(R.id.activity_match_detail_field)
    TextView fieldNameTextView;

    @Bind(R.id.activity_match_detail_joiner)
    RecyclerView mRecyclerView;

    @Bind(R.id.button)
    Button joinButton;

    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private SupportMapFragment mMap;
    private SimpleDateFormat timeInput = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat timeOutput = new SimpleDateFormat("HH:mm");
    boolean isQuickMatch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
        isQuickMatch = getIntent().getExtras().getString("type").equals("quick");
        if(isQuickMatch) {
            quickMatch = Parcels.unwrap(getIntent().getExtras().getParcelable("match"));
            ButterKnife.bind(this);
            mMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_match_detail_map);
            mMap.getMapAsync(this);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("แมตช์");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            stadiumName.setText(quickMatch.getStadiumname());
            Picasso.with(this).load(quickMatch.getImage()).into(stadiumImage);
            dateTextView.setText(quickMatch.getDate());
            timeTextView.setText(convertTimeFormat(quickMatch.getTime_from()) + " - " + convertTimeFormat(quickMatch.getTime_to()));
            reserverTextView.setText(quickMatch.getUsername());
            fieldNameTextView.setText(quickMatch.getFieldname());

            mRecyclerView.setHasFixedSize(true);
            mAdapter = new FriendMiniAdapter(quickMatch.getUser());
            mLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

            if (mAdapter.getItemCount() == 0) {
                noplayer.setVisibility(View.VISIBLE);
            } else {
//            setRecyclerHeight(mAdapter.getItemCount());
                noplayer.setVisibility(View.INVISIBLE);
            }
        }else{
            friendMatch = Parcels.unwrap(getIntent().getExtras().getParcelable("match"));
            ButterKnife.bind(this);
            mMap = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_match_detail_map);
            mMap.getMapAsync(this);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("แมตช์");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            stadiumName.setText(friendMatch.getStadiumname());
            Picasso.with(this).load(friendMatch.getImage()).into(stadiumImage);
            dateTextView.setText(friendMatch.getDate());
            timeTextView.setText(convertTimeFormat(friendMatch.getTime_from()) + " - " + convertTimeFormat(friendMatch.getTime_to()));
            reserverTextView.setText(friendMatch.getUsername());
            fieldNameTextView.setText(friendMatch.getFieldname());

            mRecyclerView.setHasFixedSize(true);
            mAdapter = new FriendMiniAdapter(friendMatch.getUser());
            mLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

            if (mAdapter.getItemCount() == 0) {
                noplayer.setVisibility(View.VISIBLE);
            } else {
//            setRecyclerHeight(mAdapter.getItemCount());
                noplayer.setVisibility(View.INVISIBLE);
            }
        }

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Server.BASEURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                QuickInterface service = retrofit.create(QuickInterface.class);
                Call<Response> call;
                if(isQuickMatch)
                    call = service.join(AppUser.getInstance().getFacebook_id(),quickMatch.getId());
                else
                    call = service.join(AppUser.getInstance().getFacebook_id(), friendMatch.getId());
                call.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(retrofit.Response<Response> response, Retrofit retrofit) {
                        if(response.body().getStatus().equals("ok")) {
                            Toast.makeText(getApplicationContext(), "คุณได้เข้าร่วมการเล่นแล้ว", Toast.LENGTH_SHORT).show();
                            finish();
                        }else
                            Toast.makeText(getApplicationContext(), response.body().getErr(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }

    private String convertTimeFormat(String time){
        try {
            return timeOutput.format(timeInput.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void setRecyclerHeight(int adapterData){
        View cardview = LayoutInflater.from(this).inflate(R.layout.cardview_friend_mini, null);
        int adapterItemSize = cardview.getHeight();
        int viewHeight = 30 * adapterData;
        Log.d(TAG, "setRecyclerHeight: " + viewHeight);
        mRecyclerView.getLayoutParams().height = viewHeight;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(googleMap != null){
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            googleMap.getUiSettings().setMapToolbarEnabled(false);
        }
        final LatLng position;
        if(isQuickMatch) {
            position = new LatLng(quickMatch.getLatitude(), quickMatch.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(position).title(quickMatch.getStadiumname()));
        }else {
            position = new LatLng(friendMatch.getLatitude(), friendMatch.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(position).title(friendMatch.getStadiumname()));
        }
//        Log.d(TAG, "getMapInstanceAndSetup: "+stadium.latitude+","+stadium.longitude);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .bearing(0)
                .tilt(0)
                .zoom(16.0f)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, null);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q="+position.latitude + ","+position.longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }
}

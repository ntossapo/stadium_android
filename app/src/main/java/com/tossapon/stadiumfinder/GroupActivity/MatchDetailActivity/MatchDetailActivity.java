package com.tossapon.stadiumfinder.GroupActivity.MatchDetailActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.tossapon.stadiumfinder.Model.Advance.QuickMatch;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MatchDetailActivity extends AppCompatActivity implements OnMapReadyCallback{

    private static final String TAG = "MatchDetailActivity";
    QuickMatch quickMatch;

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

    @Bind(R.id.activity_match_detail_joiner)
    RecyclerView mRecyclerManager;

    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    private SupportMapFragment mMap;
    private SimpleDateFormat timeInput = new SimpleDateFormat("HH:mm:ss");
    private SimpleDateFormat timeOutput = new SimpleDateFormat("HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_detail);
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

        mRecyclerManager.setHasFixedSize(true);
        mAdapter = new FriendMiniAdapter(quickMatch.getUser());
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerManager.setLayoutManager(mLayoutManager);
        mRecyclerManager.setAdapter(mAdapter);

        if (mAdapter.getItemCount() == 0) {
            noplayer.setVisibility(View.VISIBLE);
        }else{
            noplayer.setVisibility(View.INVISIBLE);
        }
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if(googleMap != null){
            googleMap.getUiSettings().setAllGesturesEnabled(false);
            googleMap.getUiSettings().setMapToolbarEnabled(false);
        }
        LatLng position = new LatLng(quickMatch.getLatitude(), quickMatch.getLongitude());
//        Log.d(TAG, "getMapInstanceAndSetup: "+stadium.latitude+","+stadium.longitude);
        googleMap.addMarker(new MarkerOptions().position(position).title(quickMatch.getStadiumname()));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .bearing(0)
                .tilt(0)
                .zoom(16.0f)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 1000, null);
    }
}

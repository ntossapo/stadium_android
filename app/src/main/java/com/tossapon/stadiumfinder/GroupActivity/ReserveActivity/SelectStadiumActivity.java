package com.tossapon.stadiumfinder.GroupActivity.ReserveActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Adapter.FieldAdapter;
import com.tossapon.stadiumfinder.Model.Basic.Field;
import com.tossapon.stadiumfinder.Model.Basic.Reservation;
import com.tossapon.stadiumfinder.Model.Basic.Stadium;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by benvo_000 on 26/1/2559.
 */
public class SelectStadiumActivity extends AppCompatActivity{

    private static final String TAG = "SelectStadiumActivity";
    @Bind(R.id.activity_field_select_recycler)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Field> fields;

    private Reservation reservation;
    private Stadium stadium;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_select);
        ButterKnife.bind(this);
        reservation = Parcels.unwrap(getIntent().getParcelableExtra("reserve"));
        stadium = Parcels.unwrap(getIntent().getParcelableExtra("stadium"));
        Parcelable[] p = getIntent().getExtras().getParcelableArray("fields");
        if(p != null){
            fields = new ArrayList<>();
            for(int i = 0; i < p.length ; i++){
                Field f = Parcels.unwrap(p[i]);
                fields.add(f);
            }
        }

        Log.d(TAG, "onCreate: " + fields.size());

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FieldAdapter(fields, reservation, stadium);
        mRecyclerView.setAdapter(mAdapter);
    }
}

package com.tossapon.stadiumfinder.GroupActivity.StadiumInformationActivity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Model.Stadium;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StadiumInformationActivity extends AppCompatActivity {
    @Bind(R.id.activity_stadium_inf_collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.activity_stadium_inf_imageView)
    ImageView imageView;

    @Bind(R.id.activity_stadium_inf_toolbar)
    Toolbar toolbar;

    @Bind(R.id.activity_stadium_inf_pager)
    ViewPager viewPager;

    Stadium data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium_information);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        data = Parcels.unwrap(getIntent().getExtras().getParcelable("stadium"));
        Picasso.with(StadiumInformationActivity.this).load(data.image).into(imageView);
        toolbar.setTitle(data.name);
    }
}

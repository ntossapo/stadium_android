package com.tossapon.stadiumfinder.GroupActivity.StadiumInformationActivity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.GroupActivity.StadiumInformationActivity.Fragment.InformationFragment;
import com.tossapon.stadiumfinder.GroupActivity.StadiumInformationActivity.Fragment.PictureFragment;
import com.tossapon.stadiumfinder.GroupActivity.StadiumInformationActivity.Fragment.TimeTableFragment;
import com.tossapon.stadiumfinder.Model.Basic.Stadium;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StadiumInformationActivity extends AppCompatActivity {

    @Bind(R.id.activity_stadium_inf_imageView)
    ImageView imageView;

    @Bind(R.id.activity_stadium_inf_toolbar)
    Toolbar toolbar;

    @Bind(R.id.activity_stadium_inf_pager)
    ViewPager viewPager;

    @Bind(R.id.activity_stadium_inf_collapse_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Bind(R.id.activity_stadium_inf_tabs)
    TabLayout tabLayout;

    Stadium data;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stadium_information);
        ButterKnife.bind(this);

        data = Parcels.unwrap(getIntent().getExtras().getParcelable("stadium"));
        type = getIntent().getExtras().getString("type");
        setSupportActionBar(toolbar);
        Picasso.with(StadiumInformationActivity.this).load(data.image).into(imageView);
        collapsingToolbarLayout.setTitle(data.name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0.0f);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0 : return InformationFragment.newInstance(data, type);
                case 1 : return TimeTableFragment.newInstance(data, type);
                case 2 : return PictureFragment.newInstance(data, type);
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return "ข้อมูล";
                case 1 : return "ตารางเวลา";
                case 2 : return "รูป";
            }
            return null;
        }
    }
}

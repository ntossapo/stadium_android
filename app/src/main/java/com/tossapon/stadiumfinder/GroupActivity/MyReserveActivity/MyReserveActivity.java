package com.tossapon.stadiumfinder.GroupActivity.MyReserveActivity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Adapter.MyReserveAdapter;
import com.tossapon.stadiumfinder.Api.ReserveInterface;
import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.GroupActivity.MyReserveActivity.Fragment.MyReserveFragment;
import com.tossapon.stadiumfinder.Model.Response.MyReserveResponse;
import com.tossapon.stadiumfinder.Network.Server;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Tossapon Nuanchuay on 30/1/2559.
 */
public class MyReserveActivity extends AppCompatActivity {

//    @Bind(R.id.activity_myreserve_recycler)
//    RecyclerView mRecycler;

    @Bind(R.id.activity_myreserve_toolbar)
    Toolbar toolbar;

    @Bind(R.id.activity_myreserve_tab)
    TabLayout tabLayout;

    @Bind(R.id.activity_myreserve_viewpager)
    ViewPager viewPager;

    private SectionPagerAdapter mPageAdapter;
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreserve);
        ButterKnife.bind(this);

        mPageAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPageAdapter);
        tabLayout.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("เล่น");
    }

    public class SectionPagerAdapter extends FragmentPagerAdapter{

        public SectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyReserveFragment.newInstance();
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0 : return "การของของฉัน";
                default:
                case 1 : return "ที่เข้าร่วม";
            }
        }
    }
}

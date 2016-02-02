package com.tossapon.stadiumfinder.GroupActivity.MyReserveActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Adapter.MyReserveAdapter;
import com.tossapon.stadiumfinder.Api.ReserveInterface;
import com.tossapon.stadiumfinder.App.AppUser;
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

    @Bind(R.id.activity_myreserve_recycler)
    RecyclerView mRecycler;

    @Bind(R.id.activity_myreserve_toolbar)
    Toolbar toolbar;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myreserve);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.BASEURL)
                .build();
        toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("สนามที่จองไว้");
        setSupportActionBar(toolbar);

        mRecycler.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mLayoutManager);

        ReserveInterface service = retrofit.create(ReserveInterface.class);
        Call<MyReserveResponse> call = service.getMyReserve(AppUser.getInstance().facebook_id);
        call.enqueue(new Callback<MyReserveResponse>() {
            @Override
            public void onResponse(Response<MyReserveResponse> response, Retrofit retrofit) {
                mAdapter = new MyReserveAdapter(response.body().getData());
                mRecycler.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}

package com.tossapon.stadiumfinder.GroupActivity.MyReserveActivity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
 * Created by benvo_000 on 9/2/2559.
 */
public class MyReserveFragment extends Fragment {

    public static MyReserveFragment newInstance() {
        MyReserveFragment fragment = new MyReserveFragment();
        return fragment;
    }

    @Bind(R.id.fragment_myreserve_recycler)
    RecyclerView mRecycler;
    @Bind(R.id.fragment_myreserve_tv)
    TextView textView;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myreserves, container, false);

        ButterKnife.bind(this, v);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.BASEURL)
                .build();

        mRecycler.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(mLayoutManager);

        ReserveInterface service = retrofit.create(ReserveInterface.class);
        Call<MyReserveResponse> call = service.getMyReserve(AppUser.getInstance().getFacebook_id());
        call.enqueue(new Callback<MyReserveResponse>() {
            @Override
            public void onResponse(Response<MyReserveResponse> response, Retrofit retrofit) {
                mAdapter = new MyReserveAdapter(response.body().getData());
                mRecycler.setAdapter(mAdapter);
                if(mAdapter.getItemCount() == 0)
                    textView.setVisibility(View.VISIBLE);
                else
                    textView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
        return v;
    }
}

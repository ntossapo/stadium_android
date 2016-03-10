package com.tossapon.stadiumfinder.GroupActivity.MyReserveActivity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Adapter.MyJoinAdapter;
import com.tossapon.stadiumfinder.Api.JoinInterface;
import com.tossapon.stadiumfinder.App.AppUser;
import com.tossapon.stadiumfinder.Model.Response.MyJoinResponse;
import com.tossapon.stadiumfinder.Network.Server;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by benvo_000 on 10/2/2559.
 */
public class MyJoinFragment extends Fragment {

    private static final String TAG = "MyJoinFragment";
    @Bind(R.id.fragment_myjoin_recycler)
    RecyclerView mRecycler;
    @Bind(R.id.fragment_myjoin_tv)
    TextView textView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public static MyJoinFragment newInstance() {
        MyJoinFragment fragment = new MyJoinFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_myjoin, container, false);
        ButterKnife.bind(this, v);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Server.BASEURL)
                .build();

        mRecycler.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecycler.setLayoutManager(mLayoutManager);
        JoinInterface service = retrofit.create(JoinInterface.class);
        Call<MyJoinResponse> call = service.getMyJoin(AppUser.getInstance().getFacebook_id());
        call.enqueue(new Callback<MyJoinResponse>() {
            @Override
            public void onResponse(Response<MyJoinResponse> response, Retrofit retrofit) {
                Log.d(TAG, "onResponse: " + response.message());
                mAdapter = new MyJoinAdapter(response.body().getData());
                if(mAdapter.getItemCount() == 0)
                    textView.setVisibility(View.VISIBLE);
                else
                    textView.setVisibility(View.INVISIBLE);
                mRecycler.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        return v;
    }
}

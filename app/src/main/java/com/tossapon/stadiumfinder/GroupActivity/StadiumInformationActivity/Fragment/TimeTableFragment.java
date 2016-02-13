package com.tossapon.stadiumfinder.GroupActivity.StadiumInformationActivity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Adapter.TimeTableAdapter;
import com.tossapon.stadiumfinder.Api.StadiumInterface;
import com.tossapon.stadiumfinder.Model.Basic.Stadium;
import com.tossapon.stadiumfinder.Model.Response.ReserveResponse;
import com.tossapon.stadiumfinder.Model.Response.TimeTableResponse;
import com.tossapon.stadiumfinder.Network.Server;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Tossapon on 24/12/2558.
 */
public class TimeTableFragment extends Fragment {
    public static Fragment newInstance(Stadium stadium, String type) {
        Bundle b = new Bundle();
        b.putParcelable("stadium", Parcels.wrap(stadium));
        b.putString("type", type);
        Fragment f = new TimeTableFragment();
        f.setArguments(b);
        return f;
    }

    @Bind(R.id.fragment_time_table_recycler)
    RecyclerView mRecyclerView;

    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;

    Stadium stadium;
    String type;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_time_table, container, false);
        ButterKnife.bind(this, v);

        stadium = Parcels.unwrap(getArguments().getParcelable("stadium"));
        type = getArguments().getString("type");

        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Server.BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        StadiumInterface service = retrofit.create(StadiumInterface.class);
        Call<TimeTableResponse> call = service.getStadiumReserve(stadium.id, type);
        call.enqueue(new Callback<TimeTableResponse>() {
            @Override
            public void onResponse(Response<TimeTableResponse> response, Retrofit retrofit) {
                mAdapter = new TimeTableAdapter(response.body().getData());
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        return v;
    }
}

package com.tossapon.stadiumfinder.GroupActivity.MainActivity.Fragment;

import android.os.Bundle;
import android.os.Parcelable;
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
import com.tossapon.stadiumfinder.Adapter.ReserveAdapter;
import com.tossapon.stadiumfinder.AppModel.AllStadiumResponse;
import com.tossapon.stadiumfinder.AppModel.Response;
import com.tossapon.stadiumfinder.Model.Stadium;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tossapon on 22/12/2558.
 */
public class ReserveFragment extends Fragment {
    @Bind(R.id.fragment_list_label)
    TextView tv;

    @Bind(R.id.fragment_list_recycler_view)
    RecyclerView mRecyclerView;

    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    public static Fragment newInstance(int sport, AllStadiumResponse res){
        ReserveFragment p = new ReserveFragment();
        Bundle b = new Bundle();
        b.putInt("sport", sport);
        b.putParcelable("data", Parcels.wrap(res.data));
        p.setArguments(b);
        return p;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        Parcelable p = getArguments().getParcelable("data");
        List<Stadium> dataSet = Parcels.unwrap(p);
        ButterKnife.bind(this, v);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ReserveAdapter(dataSet);
        mRecyclerView.setAdapter(mAdapter);
        return v;
    }
}

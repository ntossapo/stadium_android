package com.tossapon.stadiumfinder.GroupActivity.MainActivity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.AppModel.Response;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Tossapon on 22/12/2558.
 */
public class PlayNowFragment extends Fragment{
    @Bind(R.id.fragment_list_label)
    TextView tv;

    public static Fragment newInstance(int sport, Response res){
        PlayNowFragment p = new PlayNowFragment();
        Bundle b = new Bundle();
        b.putInt("sport", sport);
        b.putParcelable("data", Parcels.wrap(res));
        p.setArguments(b);
        return p;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, v);
        tv.setText("Play now");
        return v;
    }
}

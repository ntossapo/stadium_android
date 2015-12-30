package com.tossapon.stadiumfinder.GroupActivity.StadiumInformationActivity.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tossapon.projectsport.R;
import com.tossapon.stadiumfinder.Model.Basic.Stadium;

import org.parceler.Parcels;

/**
 * Created by Tossapon on 24/12/2558.
 */
public class PictureFragment extends Fragment {

    private String type;

    public static Fragment newInstance(Stadium stadium, String type) {
        Bundle b = new Bundle();
        b.putParcelable("stadium", Parcels.wrap(stadium));
        Fragment f = new PictureFragment();
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_underconstruct, container, false);
        return v;
    }
}

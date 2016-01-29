package com.tossapon.stadiumfinder.App;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Tossapon on 30/12/2558.
 */
public class LatLngModule{
    private static LatLng ourInstance;
    public static LatLng getInstance() {
        return ourInstance;
    }

    public static void newInstance(double lat, double lng) {
        ourInstance = new LatLng(lat, lng);
    }
}
